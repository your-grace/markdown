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

