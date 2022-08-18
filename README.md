IdeaRecyclerView
=================

## **概述**
+ 基于RecyclerView的二次封装，提供通用的Adapter，可以实现复杂的混合布局列表，更加直观的使用流程
+ 完全兼容RecyclerView的api
+ 存在反射实现代码，如需代码混淆，请查看附带的混淆规则说明

## **使用步骤**
### **1. 添加依赖**
  在目标module的build.gradle中：
  + 确保已经为module添加maven仓库：
    ```
    repositories {
        mavenCentral()
    }
    ```
  + 添加依赖：
    ```
    dependencies {
        implementation ('io.github.qxtx244.recyclerview:IdeaRecyclerView:1.0.1') {
            //如果想排除依赖的recyclerview库，添加如下代码：
            exclude group: 'androidx.recyclerview', module: 'recyclerview'                          
        }    
    }
    ```
  + sync项目

### **2. 添加代码**
1. 在布局xml中使用IdeaRecyclerView控件
   ```
   <com.qxtx.idea.recyclerview.view.IdeaRecyclerView
        android:id="@+id/irvList"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
   ```
2. 控件的基本配置（将泛型T替换为实际需要的类型）：
   ```
   IdeaRecyclerView<T> irvList = findViewById(R.id.irvList);
   ```
   ```
   irvList
     .option(ItemLayoutFactory)      //配置入口api，提供布局配置  
     .setListStyle(IStyle)            //配置列表样式 
     .setListData(List<T>)            //绑定列表数据  
     .setItemAction(ItemAction<T>); //列表中各个列表项的行为描述
   ```
+ **option**  
   实现一个ItemLayoutFactory接口，以参数传入，实现混合布局。如根据位置设置3种列表项布局：
   ```
   ItemLayoutFactory factory = pos -> {
          switch (pos) {
              case 1: return R.layotu.one;
              case 2: return R.layout.two;
              //...            
              default: return R.layout.def;
          }
   };
   ```
+ **setListStyle**  
  列表样式配置。预置GridStyle/LinearStyle/StaggeredGridStyle，
  也可实现IStyle/BaseStyle来完成自定义配置
+ **setItemAction**  
  列表项的行为描述，实现ItemAction<T>接口，以参数传入。
+ **通过IdeaAdapter可获得绑定的数据列表**
  ```
  List data = irvList.getIdeaAdapter().getListData();
  ```
+ **通过IdeaViewHolder获取列表项的控件对象**
  ```
  IdeaViewHolder holder = irvList.getIdeaViewHolder(itemView);
  View v = holder.getViewById(id); 
  ```
  IdeaViewHolder会缓存列表项中的控件对象，因此可以有效地避免使用findViewById()带来的重复遍历查找行为。
+ **启用/禁用列表滚动**
  如果使用了Linear对象作为列表风格，则支持主动控制列表可滚动状态，Linear将自动判断或定位到可用的滚动方向（线性列表只有一个滚动方向）
  ```
  Linear linear = new Linear(context, Linear.VER|Linear.HOR);
  irvList
        .option(...)
        .setListStyle(linear)
        .setItemAction(...)      
  //...
  linear.setScrollEnable(true|false);
  ```

### **3. 混淆配置**
如项目开启混淆，添加以下混淆规则：
  ```
  -keep class com.qxtx.idea.recyclerview.** {*;}
  ```
