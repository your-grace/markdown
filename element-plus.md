#### tip

```vue
<-- 样式穿透 -->
<style scoped>
:deep(.el-select) {
  width: 100%;
}
</style>
```

```ts
//在vue中get请求如何传递数组参数
import qs from 'qs'
import request from '@/config/axios'
await request.get({
	url: '/process/process/list?' + qs.stringify({ ids: [90, 91, 92] }, { indices: false })
})
```

#### DictLinkage

```vue
<template>
  <ElSelect v-model="valueRef" :disabled="props.disabled" placeholder="" size="default">
    <ElOption v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
  </ElSelect>
</template>

<script setup lang="ts">
//import { FormExpose } from '@/components/Form'
import { getDictOptions } from '@/utils/dict'
import { propTypes } from '@/utils/propTypes'
import { ElSelect, ElOption } from 'element-plus'
import { PropType, ref, watch, unref } from 'vue'

const props = defineProps({
  // 字典码
  dictType: propTypes.string.def(''),
  modelValue: {
    type: [String, Number, Boolean] as PropType<string | number | boolean>,
    default: null
  },
  disabled: propTypes.bool.def(false),
  options: {
    type: Array as PropType<ListItem[]>,
    default: () => []
  },
  formProps: {
    type: Object as PropType<any>,
    default: null
  },
  linkage: {
    type: Function as PropType<(formProps: any) => void>,
    default(formProps: any) {
      const data = formProps?.formExpose?.formModel as Recordable
      console.log(data?.code?.toString())
    }
  }
})

watch(
  () => props.modelValue,
  (val: string | number | boolean) => {
    if (val === unref(valueRef)) return
    valueRef.value = val.toString()
  }
)

// 输入框的值
const valueRef = ref(props.modelValue)

const emit = defineEmits(['update:modelValue'])

interface ListItem {
  value: string | number | boolean
  label: string
}

const options = ref<ListItem[]>(props.options)

onMounted(() => {
  if (options.value.length == 0 && props.dictType) {
    getDictOptions(props.dictType)?.forEach((dict) => {
      options.value.push(dict)
    })
  }
})

// 监听
watch(
  () => valueRef.value,
  (val: string | number | boolean) => {
    emit('update:modelValue', val)
    if (props.formProps) {
      props.linkage(props.formProps)
    }
  }
)
watch(
  () => props.options,
  (arr: ListItem[]) => {
    options.value = arr
  }
)
</script>
```

```ts
{
    title: '物料类型',
    field: 'materialType',
    dictType: DICT_TYPE.MATERIAL_TYPE,
    dictClass: 'number',
    isSearch: true,
    form: {
        value: '0',
        component: 'DictLinkage',
        componentProps: {
            dictType: DICT_TYPE.MATERIAL_TYPE,
            disabled: true,
            options: [],
            formProps: formProps,
            style: {
            	width: '100%'
            }
        }
    }
}
form: { value: '2' }
```

```vue
<!-- 设置表单项state只读 -->
<template #materialState="form">
	<DictTag type="material_state" :value="form.materialState" />
</template>
```

#### InputLinkage

```vue
<template>
  <ElInput v-model="valueRef" style="width: 189.5px" size="default" />
</template>

<script setup lang="ts">
import { ElInput } from 'element-plus'
import { PropType, ref, watch } from 'vue'
import { propTypes } from '@/utils/propTypes'

const props = defineProps({
  modelValue: propTypes.string.def(''),
  formProps: {
    type: Object as PropType<any>,
    default: null
  },
  linkage: {
    type: Function as PropType<(formProps: any) => void>,
    default(formProps: any) {
      const data = formProps?.formExpose?.formModel as Recordable
      console.log(data?.code?.toString())
    }
  }
})

const emit = defineEmits(['update:modelValue'])

// 输入框的值
const valueRef = ref(props.modelValue)

// 监听, 必须添加，否则校验无法通过
watch(
  () => valueRef.value,
  (val: string) => {
    emit('update:modelValue', val)
    // 数据联动
    props.linkage(props.formProps)
  }
)
</script>
```

