#### 带参上传文件

##### html表单

```html
<form id="uploadForm"  enctype="multipart/form-data">
    <input id="id" name="id" type="hidden" value="${id}">
    <table style="width: 400px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right"><label class="Validform_label"> 文件类型: </label></td>
            <td class="value">
                <t:dictSelect id="type" field="file_type" type="list" typeGroupCode="file_type" hasLabel="false" title="文件类型"
                ></t:dictSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">文件类型</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    文件上传:
                </label>
            </td>
            <td class="value">
                <input id="file" type="file" name="file" >
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">文件名称</label>
            </td>
        </tr>
    </table>
</form>
```

##### 表单处理

```javascript
<script type="text/javascript">
    function getParentWin(parentWindow) {
        if(!$("#type").val()){
            tip('请选择文件类型');
            return false;
        }
        if (!$("#file").val()) {
            tip('请上传文件');
            return false;
        }
        else {
            var fileName = $("#file").val();
            console.log(fileName);
            var fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if(fileType != "pdf"){
                tip("请上传pdf文件");
                return ;
            }
        }
        var resMsg;
        var formData = new FormData($("#uploadForm")[0]);
        $.ajax({
            url: "processRouteController.do?uploadFile",
            type: "post",
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: formData,
            success: function (result) {
                var data = $.parseJSON(result);
                if(data.success){
                    if(data.msg == "1"){
                        resMsg = data.msg;
                    }
                    else if(data.msg == "2"){
                        tip("上传文件为空");
                    }
                    else if(data.msg == "3"){
                        tip("请上传pdf文件");
                    }
                    else{
                        tip("服务异常");
                    }
                }
                console.log("上传成功");
            },
            error: function (err) {
                console.log("失败了");
            }
        })
        return resMsg;
    }

</script>
```

##### 后端处理

```java
//controller
@RequestMapping(params = "uploadFile",method = RequestMethod.POST)
@ResponseBody
public AjaxJson uploadFile(MultipartHttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "1";
    MultipartFile file = request.getFile("file");
    String id = request.getParameter("id");
    String type = request.getParameter("file_type");
    try {
        message = processRouteService.uploadFile(file,id,type);
        systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
        e.printStackTrace();
        message = "5";
        throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
}
//service
public String uploadFile(MultipartFile file,String id,String type);
//serviceImpl
//拦截的url，虚拟路径
public String pathPattern = "files";
//实际目录路径
private PropertiesUtil mProperties = new PropertiesUtil("sysConfig.properties");
String filePath = mProperties.readProperty("routeUpload");
public static final String SOP = "sop";
public static final String SAMPLE = "sample";
public static final String TEMP = "temp";
public static final String SEPARATOR = "/";

public String sopPath = filePath + SEPARATOR + SOP + SEPARATOR;
public String samplePath = filePath + SEPARATOR + SAMPLE + SEPARATOR;
public String tempPath = filePath + SEPARATOR + TEMP + SEPARATOR;

@Value(value = "${file.upload.suffix:jpg,jpeg,png,bmp,xls,xlsx,pdf}")
private String fileUploadSuffix;

/**
     * 上传
     * @param file
     * @param id
     * @param type
     * @return
     */
public String uploadFile(MultipartFile file,String id,String type) {
    String message = "1";
    if (file.isEmpty()) {
        message = "2";
        return message;
    }
    List<String> suffixList = new ArrayList<>(Arrays.asList(fileUploadSuffix.split(",")));
    try{
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (!suffixList.contains(suffix.toLowerCase())) {
            message = "3";
            return message;
        }
        String realPath = "";
        String unrealPath = "";
        if(StringUtils.equals(type,"1")){
            unrealPath = SOP;
            realPath = sopPath;
            File sopFolder = new File(sopPath);
            if (!sopFolder.exists()){
                sopFolder.mkdirs();
            }

        }
        if(StringUtils.equals(type,"2")){
            unrealPath = SAMPLE;
            realPath = samplePath;
            File sampleFolder = new File(samplePath);
            if (!sampleFolder.exists()){
                sampleFolder.mkdirs();
            }
        }
        if(StringUtils.equals(type,"3")){
            unrealPath = TEMP;
            realPath = tempPath;
            File tempFolder = new File(tempPath);
            if (!tempFolder.exists()){
                tempFolder.mkdirs();
            }
        }
        //获取工序路线信息
        ProcessRouteEntity routeEntity = systemService.getEntity(ProcessRouteEntity.class, id);
        String absolutePath = realPath + originalFilename;
        //转存
        file.transferTo(new File(absolutePath));
        //实体信息
        String hql = "from RouteFilesEntity where sourceId = '"+routeEntity.getId()+"' and type = '" + type + "'";
        RouteFilesEntity entity = systemService.singleResult(hql);
        String path = SEPARATOR + pathPattern + SEPARATOR + unrealPath + SEPARATOR + originalFilename;
        TSUser tsUser = ResourceUtil.getSessionUserName();
        if(entity != null){
            entity.setFileName(originalFilename);
            entity.setFilePath(path);
            entity.setUpdatePerson(tsUser);
            entity.setUpdateTime(new Date());
            systemService.updateEntitie(entity);
        }
        else {
            entity = new RouteFilesEntity();
            entity.setFileName(originalFilename);
            entity.setFilePath(path);
            entity.setType(type);
            entity.setSourceId(routeEntity.getId());
            entity.setCreater(tsUser);
            entity.setCreateTime(new Date());
            entity.setUpdatePerson(tsUser);
            entity.setUpdateTime(new Date());
            systemService.save(entity);
        }
    }catch (Exception e){
        message = "4";
    }
    return message;
}
```

##### sysConfig.properties

```properties
routeUpload=E://git-project//CTXMES//routeFiles
```

##### tomcat

```xml
<!-- server.xml -->
<Context docBase="E:\git-project\CTXMES\routeFiles" path="/files" reloadable="false"/>	
```



