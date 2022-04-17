IdeaRecyclerView
=================
    作者：QXTX-WIN
    创建日期：2020年4月8日

## **概述**
基于RecyclerView的二次封装，提供通用的Adapter，可以实现复杂的混合布局列表，更加直观的使用流程；  
存在反射实现代码，如需代码混淆，请查看附带的混淆规则说明
  + 集成com.android.support:recyclerview-v7：26.0.0

## **使用**
### **1. 导入工程**
+ **aar包形式**（不推荐）
  将aar包放到目标module的libs中，然后在目标module的build.gradle中，添加本地aar依赖：
  ```
  dependencies {
      implementation fileTree(dir: 'libs', include: ['*.aar'])
      //也可以用这种写法
      implementation files('libs/aar文件名')
  }
  ```
+ **依赖形式**  
  在目标module的build.gradle中：  
  添加maven仓库路径：
  ```
  repositories {
      maven { url 'maven仓库地址' }
  }
  ```
  添加依赖：
  ```
  dependencies {
      implementation ('com.qxtx.idea.recyclerview:IdeaRecyclerView:1.0.0') {
            //如果想排除依赖的recyclerview-v7库，则添加以下代码：
            exclude group: 'com.android.support', module: 'recyclerview-v7'             
      }    
  }
  ```
  然后执行sync

### **2. 添加代码**
1. 在xml中使用IdeaRecyclerView控件
   ```
   <com.qxtx.idea.recyclerview.view.IdeaRecyclerView
        android:id="@+id/irvList"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
   ```
2. 控件的基本配置：
   ```
   IdeaRecyclerView<T> irvList = findViewById(R.id.irvList);
   ```
   ```
   irvList
     //配置入口api，提供布局配置
     .option(ItemLayoutFactory)
     //配置列表方向  
     .setListStyle(IStyle)
     //绑定列表数据  
     .setListData(List<T>)
     //列表中各个列表项的行为描述  
     .setItemAction(ItemAction<T>);  
   ```
同时，完全支持RecyclerView的api
