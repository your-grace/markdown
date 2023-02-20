#### vuex

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

