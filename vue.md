#### vuex和router

```javascript
//actions异步执行，通过dispatch触发；mutations同步执行，通过commit触发修改states
store.dispatch('Logout').then(() => {
	next({ path: '/user/login', query: { redirect: to.fullPath } })
})
// 登出
Logout({ commit, state }) {
    return new Promise((resolve) => {
        let logoutToken = state.token;
        commit('SET_TOKEN', '')
        commit('SET_PERMISSIONLIST', [])
        logout(logoutToken).then(() => {
            if (process.env.VUE_APP_SSO == 'true') {
                let sevice = 'http://' + window.location.host + '/'
                let serviceUrl = encodeURIComponent(sevice)
                window.location.href = process.env.VUE_APP_CAS_BASE_URL + '/logout?service=' + serviceUrl
            }
            resolve()
		}).catch(() => {
            reject(error)
        })
    })
}
//路由模式：hash和history
//实例方法
this.$router.push({path:"/home/recommend", query:{wd:1,offset:0}})
this.$router.replace({path:"/home/recommend", query:{wd:1,offset:0}})
this.$router.go(3)   // 从当前路由history栈的位置前进3条
this.$router.go(-1) // 从当前路由history栈的位置后退1条
this.$router.go(0)  // 强制刷新页面
this.$router.back() // 等价于this.$router.go(-1)
this.$router.forward() // 等价于this.$router.go(1)
//路由懒加载
import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)
const routes = [
  {
    path: '/mock',
    name: 'Mock',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Mock.vue')
  }
]
const router = new VueRouter({
  routes
})
export default router
//路由守卫
export default {
  props: ['id'],
  data() {
      return {
          musicUrl: ''
      }
  },
  beforeRouteEnter (to, from, next) {
    // 在渲染该组件的对应路由被 confirm 前调用
    // 不！能！获取组件实例 `this`
    // 因为当守卫执行前，组件实例还没被创建
    console.log(undefined)
  },
  beforeRouteUpdate (to, from, next) {
    // 在当前路由改变，但是该组件被复用时调用
    // 举例来说，对于一个带有动态参数的路径 /foo/:id，在 /foo/1 和 /foo/2 之间跳转的时候，
    // 由于会渲染同样的 Foo 组件，因此组件实例会被复用。而这个钩子就会在这个情况下被调用。
    // 可以访问组件实例 `this`
  },
  beforeRouteLeave (to, from, next) {
    // 导航离开该组件的对应路由时调用
    // 可以访问组件实例 `this`
  }
}
next()  // 允许路由跳转 
next(true) // 允许路由跳转
next(false) // 不允许路由跳转
next('/') / next({ path: '/' })
//路由独享守卫
const router = new VueRouter({
  routes: [
    {
      path: '/foo',
      component: Foo,
      beforeEnter: (to, from, next) => {
        // ...
      }
    }
  ]
})
const routes = [
    {
        path: '/mine',
        component: () => import('../views/Mine'),
        //路由独享守卫
        beforeEnter(to, from, next) {
            // 因为这个守卫没有任何DOM操作或者对组件自身状态进行读写
            // 这样的守卫就可以作为路由独享守卫
            // 正确的做法存在cookie storage中
            if (localStorage.getItem("user")) {
              next();
            } else {
              // 这里吗没有this, next接收一个回调函数,在回调函数中跳转
              // 下面的写法进入了个人页面,又从个人页面重定向到登录,这样可能会造成一些不必要的bug
              //   next((vm) => {
              //   vm.$router.replace('/landr')
              //   });
              next({name:'login',params:{to}}); //阻止本次跳转,直接导航到指定路由
            }
          }
    },
    {
        path: '/landr', // login an register
        component: () => import('../views/loginanregister/LoginAndRegister'),
        children: [
            {
                name:'login',
                path: 'login',
                component: () => import('../views/loginanregister/Login')
            },
            {
                path: 'register',
                component: () => import('../views/loginanregister/Register')
            }
        ]
    }
]
```

#### 父组件

```vue
<template>
  <button @click="isclick">父组件button</button>
  <HelloWorld msg="Hello Vue 3 + Vite" @updata="updata" ref="zihello" v-model="num3" />
</template>
<script setup>
import { ref } from "vue";
import HelloWorld from "./components/HelloWorld.vue";
const num3=ref()
const zihello = ref();
const isclick = () => {
  console.log(zihello);
  console.log("接收ref暴漏出来的值", zihello.value.num);
  console.log("接收reactive暴漏出来的值", zihello.value.num2);
};
const updata = (data) => {
  console.log(data); //我是子组件的值
};
</script>
```

#### 子组件

```vue
<template>
  <div>
    <div>num:{{num}}</div>
    <div>num2:{{num2}}</div>
    <div>msg:{{msg}}</div>
    <hr />
    <button @click="ziupdata">子传父</button>
    <button @click="updata">修改num/num2</button>
    <hr />
    <div>{{doubleCount}}</div>
  </div>
</template>
<script setup>
//setup语法糖，不需要return出去
import {
  ref,
  reactive,
  toRefs,
  toRef,
  computed,
  watch,
  watchEffect,
  nextTick,
  // useRoute,
  // useRouter,
} from "vue";

const num = ref(2);
//reactive定义引用数据类型，将复杂数据属性或数据项声明为响应式，底层实现es6的proxy
const num2 = reactive({ a: 1, b: 2 });
const updata = () => {
  myTitle.value = 10;
  num.value = 1111;
  num2.b = 3333333;
};
//父传子defineProps
//用来接受props的Api
const props = defineProps({
  msg: {
    type: String,
    default: "我是默认值",
  },
});
console.log(props);
//子传父defineEmits
const ziupdata = () => {
  em("updata", "我是子组件的值");
};
// 子组件向父组件传值defineEmits
const em = defineEmits(["updata"]);
//暴露出自己的属性，在父组件中可以拿到defineExpose，组件引入别名.value.num/.value.num2.a
defineExpose({
  num,
  num2,
});
//toRefs,常用于es6的解构赋值操作,因为在对一个响应式对象直接解构时解构后的数据将不再有响应式，而使用toRefs可以方便解决这一问题。
const { a, b } = toRefs(num2);
console.log(a.value);
//toRef,引用num2中的属性a，会影响到num2中原始数据a的改变
const myTitle = toRef(num2, "a");
//计算属性computed
const doubleCount = computed(() => {
  return num.value * 2;
});
//监听watch
// 监听使用ref定义的变量的时候时候,第一个参数直接使用
watch(num, (newValue, old) => {
  // console.log("num", newValue, old);
});
//使用reactive定义的变量需要没在监听的时候需要使用函数返回值的形式才能监听到
watch(
  () => num2,
  (newValue, old) => {
    // console.log(1111, newValue, old);
  },
  {
    deep: true,
    immediate: true,
	//lazy:true,初始化不会调用watch
  }
);
// 监听watchEffect,立即执行传入的一个函数，并响应式追踪其依赖，并在其依赖变更时重新运行该函数。
watchEffect(() => {
  console.log(num.value, "改变");
});
//v-modal类似sync
//子组件
const emit = defineEmits(["update:num"]);
const changeInfo = () => {
   //触发父组件值更新
   emit("update:num", 100);
};
// 父组件使用v-model接收
//nextTick
nextTick(() => {
  // ...
});

//useRoute, useRouter
// 必须先声明调用
const route = useRoute();
const router = useRouter();
// 路由信息
console.log(route.query);
// 路由跳转
router.push("/newPage");
// onBeforeRouteLeave, onBeforeRouteUpdate
// 添加一个导航守卫，在当前组件将要离开时触发。
onBeforeRouteLeave((to, from, next) => {
 next();
});
// 添加一个导航守卫，在当前组件更新时触发。
// 在当前路由改变，但是该组件被复用时调用。
onBeforeRouteUpdate((to, from, next) => {
  next();
});
</script>
```

#### 自定义事件-emit

```javascript
this.$emit("input",i);
this.$emit("change",i);
```
这段代码使用了Vue的$emit方法触发了两个事件，分别是input和change。
1. `$emit("input", i)`：这行代码触发了一个名为`input`的自定义事件，并将`i`作为事件的参数传递。`input`事件通常用于实现双向数据绑定，即当输入框的值发生变化时，通过触发`input`事件可以将最新的值传递给父组件或监听了该事件的其他组件。父组件或其他组件可以通过`v-model`指令或监听`input`事件来接收并更新`i`的值。
2. `$emit("change", i)`：这行代码触发了一个名为`change`的自定义事件，并将`i`作为事件的参数传递。`change`事件通常用于在输入框的值发生变化时进行一些额外的操作或处理。父组件或其他组件可以通过监听`change`事件来获取输入框的最新值，并执行相应的逻辑，如校验、提交表单等。

需要注意的是，`$emit`是Vue实例提供的方法，用于在组件中触发自定义事件。通过触发自定义事件，可以实现组件之间的通信和数据传递。父组件或其他监听了这些事件的组件可以通过模板中的指令（如`v-model`）或监听器来接收和处理这些事件。

#### nextTick
##### 使用场景1.访问更新后的DOM
```vue
<template>
  <div>
    <p ref="msg">Hello, World!</p>
  </div>
</template>
<script>
export default {
  mounted() {
    // 此时访问DOM元素是无法获取到更新后的text值，需要使用nextTick方法
    console.log(this.$refs.msg.innerText); // 输出：Hello, World!
    this.$nextTick(() => {
      console.log(this.$refs.msg.innerText); // 输出：Hello, Vue!
    });
  },
  methods: {
    updateMessage() {
      this.$refs.msg.innerText = 'Hello, Vue!';
    }
  }
}
</script>
```
##### 使用场景2.在更新后执行某些操作
```vue
<template>
  <div>
    <button @click="changeColor">Change Color</button>
    <p :style="{ color: textColor }">Hello, World!</p>
  </div>
</template>
<script>
export default {
  data() {
    return {
      textColor: 'black'
    }
  },
  methods: {
    changeColor() {
      // 使用nextTick确保视图已经更新完成
      this.$nextTick(() => {
        this.textColor = 'red';
      });
    }
  }
}
</script>
```
##### 原理
```text
核心技术：
1.Promise	2.microtask
执行顺序：
1. 当我们调用$nextTick()方法时，Vue.js会将回调函数添加到一个内部的回调数组中。 
2. 如果当前没有处于等待状态的更新操作，则将一个异步的微任务添加到微任务队列中。 
3. 在当前宏任务执行完毕后，开始执行微任务队列中的所有微任务，包括$nextTick()的回调函数。 
4. 在执行每个微任务之前，Vue.js会进行一些性能优化的处理。它会检测浏览器是否支持原生的Promise对象，如果支持，则使用Promise来创建微任务；如果不支持，则使用MutationObserver来创建微任务。 
5. 执行微任务队列中的所有微任务，依次执行$nextTick()的回调函数。 
6. 在每个回调函数执行完毕后，Vue.js会清空回调数组，以便下一次调用$nextTick()时重新填充回调函数。 
7. 在微任务执行完毕后，浏览器会进行UI Render，更新页面的可视化效果。 
总结起来，$nextTick()的执行顺序是在当前宏任务执行完毕后，立即执行微任务队列中的所有微任务，包括$nextTick()的回调函数。这保证了回调函数能够在DOM更新后立即执行，而不需要等待下一个宏任务。  
需要注意的是，由于微任务的执行优先级高于宏任务，所以$nextTick()的回调函数会在其他宏任务之前执行。这确保了回调函数能够及时获取到DOM更新后的状态，并进行相应的操作。

微任务和宏任务执行顺序：
1. 执行一个宏任务（例如整体代码块）。 
2. 检查微任务队列，如果有微任务，则依次执行所有微任务直到队列为空。 
3. 执行浏览器UI Render，更新页面的可视化效果。 
4. 执行下一个宏任务（如果有的话），并重复上述步骤。 
总结起来，宏任务和微任务的执行顺序是交替进行的（事件循环机制）。每次执行一个宏任务后，会检查并执行所有微任务，然后进行UI Render，然后再执行下一个宏任务。
```
##### nextTick与updated的区别
> `$nextTick` 方法确保在下一次DOM更新循环结束之后执行回调函数。在数据修改后，Vue会重新渲染DOM，并在渲染完成后执行 `$nextTick` 的回调函数。这样可以确保在操作或访问DOM之前，DOM已经完成了渲染。 
> `updated` 钩子函数是在组件的VNode更新完成后调用。当组件的数据发生变化，导致重新渲染组件的VNode时， `updated` 钩子函数会被触发。这意味着在 `updated` 钩子函数中，您可以访问到更新后的DOM元素。
> 区别：都是数据修改后，可以获取新数据，`$nextTick`执行在渲染前，`updated`在渲染后
