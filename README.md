# Android 模仿粤苗APP界面功能实现

## 0. 题目要求

题目：我的 粤苗APP

\1. 粤苗APP界面

Ø 基于已完成的“APP调研报告”相关的经验总结，尽量模拟如下粤苗APP（华为应用市场：粤苗）的功能，即参考如下的界面展示形式及功能模块：

![image-20230620111831812](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620111831812.png)![image-20230620111855908](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620111855908.png)![image-20230620111909475](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620111909475.png)

图1 粤苗APP；功能模块及其高清图可以去APP查看

\2. 具体要求

模拟图1所示粤苗APP，介绍该APP与服务相关的一些知识点并能提供相应功能：

1） 建议包含的一些功能：活动之间的转换与数据传递；能适应不同的展示界面；有登录功能，强制下线功能；数据有多样化的持久化功能；能跨程序提供与共享数据；有展示一些多媒体的功能；

2） 较好的实现了书本上介绍的一些较成熟的功能，并能较好的把这些功能融合在一个完整且无大bug的APP里；

3） 能在此基础上构建自己的报告亮点，如实现了书本不一样的功能模块，或者为某个知识点找到适当的新的应用场景，或者能解决同学们普遍存在的一些问题等；

4） 模拟的APP不局限于所参照APP的功能，即只需尽量模拟这些功能，**不要求将每个界面或功能都实现，如果某个界面或功能不能体现已学知识点，可以不用考虑**，当然如果能想办法实现出来，可以作为报告亮点；即**不必与这些界面或功能完全一样，可在此基础上进行变通，达到类似的效果就可以**；如果设计了一些该APP没有的功能，请说明清楚这些功能的实现方式、潜在的用途等；

5） **总体目标是考察同学们是否已掌握课程内容，是否能灵活利用所学的知识点**，做到每个功能各种实现方式的丰富化（如数据的持久化的三种实现方式都能在APP中有所体现），并且能了解并总结不同实现方式的优劣，且能通过APP的实现体现出来；

\3. 部分参考

1）功能实现参考：图第1-3列尽量参考第6章数据持久化技术的各个知识点；第1列尽量参考布局及活动之间的跳转，碎片的实现，多媒体展示功能；第1,2列可以利用Tablayout、ViewPager或RecyclerView；

2）设计Android基于位置的服务，比如能根据用户所在位置查找最近的服务或门诊网点；添加一个小功能，整合网络技术的应用，比如“收藏”的功能，或个人接种记录的线下保存；利用数据后台下载的功能，比如接种手册或接种指导视频的下载等；

3）可以借鉴的部分章节内容，第12章可以让你的APP界面变得更美观；第14章展示了一个大型的工程，可以学习下多个功能怎样在一个工程里体现；

\4. 其它要求

1）构建的APP要格式工整，美观；个人补充的新的功能或界面可以与“APP调研报告”的总结对应上；

2）实验报告中需要有功能的描述、实验结果的截屏图像及详细说明；结果展示要具体，图文交叉解释；代码与文本重点要突出；建议报告采用白色背景的形式；

3）也欢迎采用课程后续章节的知识点完成本次大作业，如果实现的功能言之合理，会考虑酌情加分；

4）每位同学在最后一次课都需要上台报告，并且最好能现场演示APP的功能等，没上台报告的同学分数会受一定的影响；

5）报告由个人独立完成。



## 目录

[TOC]

> 开发语言：Java
>
> 开发工具：Android Studio
>
> **开发用到的外部包**：
>
> ```java
>     //三个依赖，用于Tablayout，viewpager，recyclerView布局
>     implementation 'androidx.legacy:legacy-support-v4:1.0.0'
>     implementation 'com.google.android.material:material:1.5.0'
>     implementation 'androidx.recyclerview:recyclerview:1.2.1'
>     //数据库的litepal
>     implementation files('libs/litepal-2.0.0-src.jar')
>     //百度地图API
>     implementation files('libs/BaiduLBS_Android.jar')
>     //引用别人的百度地图项目时用到的
>     implementation 'com.scwang.smartrefresh:SmartRefreshLayout:1.1.0-alpha-21'
>     //二维码扫描依赖
>     implementation 'com.google.zxing:core:3.3.3'
>     implementation 'com.journeyapps:zxing-android-embedded:3.6.0'
> 
> ```
>
> 注意！！！
>
> 1. 这里可能有的人版本是support-v4对应的包
>
>    添加了support-v4的包后发现build.gradle（Gradle Scripts文件夹下）有报错，
>
>    报错内容类似`Version 28 (intended for Android Pie and below) is the last version of the legacy support library`
>
>    这是因为当前的AS已经不支持这些旧的包，因此可以将support-v4迁移到AndroidX，具体方法参考下面的文章或者自行搜索：
>
>    [(174条消息) Version 28 (intended for Android Pie and below) is the last version of the legacy support library_谷哥的小弟的博客-CSDN博客](https://blog.csdn.net/lfdfhl/article/details/105269551)
>
> 2. litepal的引入是**本地**下载好并且导入的依赖
>
>    这里尝试过直接引入`implementation 'org.litepal.guolindev:core:3.2.0'`或者是`implementation 'org.litepal.android:core:3.2.0'`
>
>    但是发现在Java，manifest文件当中根本找不到喝这个库相关的内容
>
>    于是就直接到github上面下载jar包并且导入了
>
>    给出下载地址 ： https://github.com/guolindev/LitePal#latest-downloads/
>
> 3. 百度地图的API（LBS SDK）也是要上网下载到**本地**的，但是会有不同的包选择
>
>    所以如果是使用别人的项目，那么则建议直接用别人libs下面的LBS SDK包，否则可能出现不兼容的情况



## 1. 效果展示

> 全部功能实现功能如下：、
>
> **基本功能：**
>
> - 用户登录（数据持久化）：**数据库文件存储和读取**账号密码
> - 退出登录（广播机制实现强制下线）
> - 提示网络中断（广播机制实现网络监听）
> - 使用**SharedPreference**实现记住密码功能（数据持久化）：
> - 点击页面打开嵌入式Web内容（使用WebView）
> - **侧边栏**放置用户信息条（NavigationVIew）
> - 新闻内容使用Tab实现切换（ViewPager）
> - 使用RecyclerView实现多种布局（线性，瀑布式，网格等）
> - 网格菜单布局GridLayout
>
> **新功能：**
>
> - zxing库实现**扫描、生成二维码**（跨程序共享数据，Thread）
> - 百度API（LBS SDK）实现地图以及寻找附近门诊功能（位置服务）
> - 受种者信息实现CRUD增删改查（数据库操作）
> - 轮播图实现（ViewPager，**多线程**）

- 首先打开APP页面先进入的是登录界面，可以选择**记住密码**

  ![联想截图_20230619171717](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230619171717.png)

- 登录成功后进入首页，页面从上到下是**轮播图**，**预约专题菜单**、**新冠相关新闻**

  ![image-20230619173947816](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619173947816.png)

  点击左上角的图标可以展示个人信息侧边栏

  首页下方的新闻可以实现fragemnt的**页面切换**，新闻具体页面用**WebView嵌入网页**对应内容

- 首页点击预约专题当中的**团体预约**：

  ![联想截图_20230619173712](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230619173712.png)

  通过输入任意数字，英文，中文都可以生成属于自己的二维码，也可以点击扫描别人的二维码

  从而获取团体码数字内容

- 点击底部导航栏，切换到**门诊**

  ![image-20230619175026770](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619175026770.png)

  门诊使用了百度地图的API，点击社康，门诊，医院三个按钮，可以基于当前的位置搜索

  到附近最近的门诊等，如图为搜索麦当劳的结果

- 最后切换到个人页面，可以点击进去查看受种者信息

  ![image-20230619175451553](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619175451553.png)

  可以对受种者的信息实现增删改查，也可以将某个受种者绑定到当前的用户



## 2. 基本功能介绍

> 这一部分先简单地介绍一下一些基本的页面功能，大都是之前的实验曾经实现过的
>
> 首先看一下Java文件的布局，文件较多，因此按照页面来进行分类
>
> ![image-20230619181107797](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619181107797.png)
>
> 主要页面为四个，分别是登陆页面、首页、门诊页面、个人主页
>
> 还有一些子页面，例如团体码扫描，受种者信息等在下面会详细展开

### 2.1 顶部栏ToolBar以及侧滑栏Navigation

> 顶部菜单栏：
>
> ![联想截图_20230619183236](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230619183236.png)
>
> 侧边栏：
>
> ![联想截图_20230619183300](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230619183300.png)
>
> **布局文件**：
>
> - activity_main.xml ：主活动页面
> - toolbar_menu：菜单栏图标（右侧的分享和个人页面的logo）
> - nav_header,xml ：侧滑栏的头部信息（头像，昵称，联系方式）
> - left_drawer.xml：侧滑栏的菜单内容
>
> **java文件**：
>
> - MainActivity
> - NavigationUtil

#### 2.1.1 导入导航栏依赖

导入”NavigationView“的依赖

~~~xml
implementation 'com.android.support:design:29.0.1'
~~~

#### 2.1.2 配置toolbar菜单栏布局文件

关于toolbar的各种设置，可以参考以下文章

[(174条消息) Android Toolbar的使用详解_暗恋花香的博客-CSDN博客](https://blog.csdn.net/qq_42324086/article/details/117390236)

**设置为NoActionBar**：

要使用toolbar，首先要去掉系统默认设置的ActionBar

在AndroidManifest.xml文件当中可以设置活动的主题，我们新建一个NoActionBar的主题，并且给我们需要配置toolBar的页面赋予这个主题即可

**AndroidMenifest.xml**

![image-20230619183942699](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619183942699.png)

**themes.xml**

![image-20230422164201720](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230422164201720.png)

看到最右边设置了`noActionBar`，这样才可以让toolbar正常显示

**配置toolBar**

需要在`activity_main.xml`文件当中放入toolBar，因为是在首页主活动放置toolBar

**activity_main.xml部分代码**

```xml
<!--    顶部菜单栏toolBar-->
    <androidx.appcompat.widget.Toolbar
        android:id="@+id/my_toolbar"                                   				      	 android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
        app:popupTheme="@style/ThemeOverlay.AppCompat.Light"
       	......
/>
```

除了在活动布局文件中放置toolbar，还需要一个菜单图标文件

**toolbar_menu.xml**

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
        android:icon="@drawable/share"
        android:orderInCategory="80"
        android:title="edit"
        app:showAsAction="ifRoom|withText" />
    <item
		.......... />
<!--    后面有更多的item如果放不下就会进入菜单栏-->
</menu>
```

这里的配置和actionBar的menu绘制方法类似，唯一区别就是`app:showAsAction`不同，该属性是Toolbar当中很关键的属性

其中四个不同的值的作用分别如下：

1）always：这个值会使菜单项一直显示在 ToolBar上。
2）ifRoom：如果有足够的空间，这个值会使菜单项显示在 Tool Bar上。
3）never：这个值会使菜单项永远都不出现在 ToolBar上。
4）withText：这个值会使菜单项和它的图标、菜单文本一起显示。一般和ifRoom一起通过“|”使用
app:showAsAction 属性值为 ifRoom|withText，表示如果有空间，那么就连同文字一起显示在标题栏中，否则就显示在菜单栏中。
而当app:showAsAction 属性值为 never时，该项作用为Menu不显示在菜单组件中。

这个文件后面我们会在**activity的Java代码**当中写入并且连接上

**MainActivity.java**

```java
myToolbar.inflateMenu(R.menu.toolbar_menu);
```



#### 2.1.3 配置侧滑栏xml文件

添加NavigationView组件（要使用该组件需要从外部导入design包，在前面有提到导入的具体的包）

将该组件添加到toolbar所在的活动的页面当中，也就是`activity_main.xml`

**activity_main.xml部分代码**

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.drawerlayout.widget.DrawerLayout
    ...............>

    ...............//其他布局代码
    
<!--左侧导航菜单-->
<com.google.android.material.navigation.NavigationView
	..............
    app:headerLayout="@layout/nav_header"
    app:menu="@menu/left_drawer" />
</androidx.drawerlayout.widget.DrawerLayout>
```

这个菜单栏由于是侧滑出现的，所以放在整个布局的最外面，并且需要用`DrawerLayout`包裹

注意看NavigationView里面有两个app的属性`app:headerLayout`和`app:menu`

因此这里需要新建两个文件，一个是头部的代码`nav_header`，一个是下面的菜单的部分`left_drawer`

![image-20230619184836054](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619184836054.png)

对应APP页面内容如上，left_drawer是一个菜单menu文件，代码比较简单就不展示了

### 2.2 首页内容 网格菜单 

网格的内容比较简单，对应菜单样式

![image-20230619185152433](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619185152433.png)

用了两层layout进行嵌套实现了上下不同的宽度，不同个数的菜单

~~~xml
<GridLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:columnCount="1"
    android:rowCount="2"
    android:layout_gravity="center"
    android:orientation="horizontal"
    <GridLayout  >
        三个<Button />
    </GridLayout>
<!--    第二行-->
    <GridLayout >
        五个<Button />
    </GridLayout>
</GridLayout>

~~~



### 2.3 首页内容 新闻列表（TabLayout + ViewPager + Fragment）

>通知列表使用了上述三样东西结合
>
>TabLayout：提供选项切换到不同的菜单
>
>ViewPager：用于滑动切换到不同的菜单
>
>Fragment：每个菜单显示的页面内容
>
>**实现效果**：
>
>![联想截图_20230619215643](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230619215643.png)
>
>**涉及配置xml文件**：
>
>- fragment_homepage.xml （左侧碎片布局）
>- main_grid_menu.xml （网格菜单）
>- selected.xml （选中菜单的颜色设置）
>- themes.xml  （设置菜单的文件大小）
>- listview_item.xml （单条新闻列表的样式）
>- fragment_news_list/xml （新闻列表，只含一个listview的碎片）
>
>**Java代码文件**：(package com.example.yuemiaoapp.homepage.fragment;)
>
>- HomepageFragement （整体主页的碎片）
>- NewsFragmentPagerAdapter（用于切换列表的适配器）
>- NewsListFragement（新闻列表碎片，用于展示不同的碎片）

#### 2.3.1 界面配置文件

在主页的碎片文件中放入`TabLayout`和`ViewPager`控件

~~~xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	.................>
    <!--顶部导航栏，切换选项卡-->
    <com.google.android.material.tabs.TabLayout
        app:tabBackground="@drawable/selected"
        app:tabTextAppearance="@style/MyTabLayoutTextAppearance"
		................/>
    <!--用于实现左右滑动效果-->
    <androidx.viewpager.widget.ViewPager
		........./>
</LinearLayout>
~~~

看到上面在TabLayout当中引入了两个新的东西：

分别是`selected.xml`和`themes.xml`当中的`MyTabLayoutTextAppearance`

还有一个listview

#### 2.3.2 java 代码

这里稍微说明一下**实现的步骤**

- 初始化ViewPager和TabLayout

  **HomepageFragment.java**

  ~~~java
          //将页面绑定viewPager，进行设置
          initViewPager();
          //初始化layout的设置，例如图标，定位
          initTabLayoutView();
  ~~~

- 使用开源的`litepal`库从数据库当中获取数据

  **NewsListFragment.java**

  ```java
  private void initData() {
      //根据label标签条件查询处相应的文章
      mList = LitePal.where("label = ?",label).find(Notification.class);
  }
  ```

  可以看到获取数据库的数据是非常简单的，数据库的配置这里就不详细介绍了

  使用LitePal库配置是比较简单的

  对应数据库表的内容：

  ![image-20230619224456710](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619224456710.png)

  这些刚好对应了每一条新闻列表上面的数据

  点击之后就会根据url的内容跳转到一个带有**WebView**的页面，从而将新闻内容渲染出来

- 渲染到`listView`控件上面

  成功获取数据后，我们会使用**Adapter**类来对页面的listView进行渲染

  这里由于为了方便，就将对应的适配器类`NewsAdapter`放在`NewsListFragment.java`文件当中了

  既然是listview那么就会有对应的listview_item.xml来对应每一条list的样式内容

  **listview_item.xml**

  ![image-20230619224916443](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619224916443.png)

  上面是对应的每条新闻列表的样式



### 2.4 登录页面（sharedPreference存储密码 + 数据库校对用户信息）

> **xml文件**：
>
> activity_login.xml
>
> **登录账号**：
>
> 详见下面的数据库
>
> **java 相关文件**：
>
> - LoginActivity （登录页面对应的活动）
> - BaseActivity  （增加监听广播的内容，所有类的基类）
> - ForceOffLineReceiver （广播：用来强制下线）
>
> **登录界面**：
>
> ![联想截图_20230619171717](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/联想截图_20230619171717.png)

这里的布局文件只有一个，就不详细介绍了

包裹输入内容的**透明背景**使用zxing库自带的`#00000000` 

说一下用了哪些主要的控件：

- editText：输入框，可通过`android:theme`设置颜色风格
- CheckBox：勾选框

#### 2.4.1  用户信息校对

从**数据库**当中获取用户的所有信息，和当前输入框的信息进行校对

**LoginActivity**

~~~java
            public void onClick(View view) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                //如果账号是admin且密码是123456,就认为登录成功
                userInfoList = LitePal.findAll(UserInfo.class);//获取密码数据库信息
                for(UserInfo info : userInfoList){
                    if(info.getAdmin().equals(account.trim()) && info.getPassword().equals(password.trim()))
                    { //密码正确后的逻辑代码}
                }
~~~

看到上面的代码通过**litepal库**

~~~java
userInfoList = LitePal.findAll(UserInfo.class);//获取密码数据库信息
~~~

这一行来获取**数据库中该表的所有数据**

用户信息表userinfo的内容如下，有密码，姓名等信息

![image-20230619225815739](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619225815739.png)

最后面有一个inoculate_id是用在受种者的功能处的，用于绑定当前使用受种者去预约疫苗记录等操作

#### 2.4.2 记录用户数据（SharedPreference）

为了让用户登录更加方便，在登录页面当中，**记住密码**这个功能是必不可少的

而用SharedPreference可以很方便地实现这个功能

代码也很简单

**LoginActivity.java**

~~~java
SharedPreferences pref;
SharedPreferences.Editor editor = pref.edit();
if(rememberPass.isChecked()){ //检查是否勾选了记住密码
    //使用Editor来添加数据存储
    editor.putBoolean("remember_password",true);
    editor.putString("account",account);
    editor.putString("password",password);
}
else editor.clear();
~~~

上图也就十行代码，只需要直接调用SharedPreference的库提供的内容

在检测到用户勾选了”记住密码“之后，就将数据都put到editor当中，这样就已经将用户的信息存在本地了，它的具体文件位置可以上网搜索

下次再次进入到LoginActivity的时候，就可以直接调用存在SharedPreference的数据了

~~~java
pref = PreferenceManager.getDefaultSharedPreferences(this);
boolean isRemember = pref.getBoolean("remember_password",false);
if(isRemember){
    //将记住的内容直接设置上去
    accountEdit.setText(pref.getString("account",""));
    passwordEdit.setText(pref.getString("password",""));
    rememberPass.setChecked(true);
}
~~~



### 2.5 数据库配置 （文件读写 + LitePal库）

> **相关配置文件**：
>
> - litepal.xml
> - AndroidManifest.xml
> - SchioolDemo.db
> - build.gradle
>
> **相关Java文件**：
>
> DatabaseUtil.java

#### 2.5.1 导入依赖以及配置

这里我学习了《第一行代码》当中给出的LitePal的库的使用并且进行了导入

不过这里用给出的**导入方法或者网上的**都不行，于是只能到github直接将**Jar包**下载下来了。

**配置数据库方法**

这里书上也有教程，这里就简单介绍一下

- 导入依赖

  在build.gradle（Module.app）当中导入

  ~~~java
  implementation files('libs\\litepal-2.0.0-src.jar')
  ~~~

- 在main当中新建assets文件夹，并新建一个litepal.xml

  ![image-20230518173736578](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230518173736578.png)

  该文件用于配置数据库和Java当中的类的关系映射

  ~~~xml
  <litepal>
      <dbname value="MyDBDemo"></dbname>
      <version value="3" ></version>
      <list>
  <mapping class="com.example.yuemiaoapp.entity.Notification"></mapping>
   ..........</list>
  </litepal>
  ~~~

  - dbname：数据库名字为”SchoolDemo“，然后一共有三个数据表，

  - version ：value就是数据表的个数
  - list：对应每一个Java当中的类的一个映射

- 在`AndroidManifest.xml`当中配置一下

  ~~~xml
      <application
          android:name="org.litepal.LitePalApplication"
                   .......></application>
  ~~~

配置成功后，可以通过android studio自带的**Device File Explorer**来查找虚拟机当中的文件

它在虚拟机文件当中的路径为`data/data/com.example.mewsapppage/databases`

不过这个路径在本机电脑当中是不实际存在的，也就是说你无法在**虚拟机关机**的情况下去**修改这个文件**.

#### 2.5.2 存储提前准备好的数据文件

由于考虑到每次到一台新的机器上面，都会创建一个新的数据库db，因为它是不存在本地的，因此我们要去读取它的文件

为了避免在Java当中还要手动写代码去导入数据到数据库当中，而且数据也不方便修改

于是可以采用将**存在安卓资源文件中的db数据库** 写入 到android手机存储db数据库的位置当中，这样就实现了文件的覆盖，可以使用预先写好数据的数据库了！

这就涉及到文件的读写操作了，我们将数据库文件存放在assets目录下面

![image-20230619231802297](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619231802297.png)

名字也要和我们要生成的安卓手机上的数据库**文件名字一致**，接着编写代码进行IO操作

**DatabaseUtil.java**

首先在Java当中获取存取的路径名称

~~~java
String DB_PATH = "/data/data/" + context.getPackageName() +"/databases/";
String DB_NAME = "MyDBDemo.db";
~~~

接着使用`InputStream` 和`FileOutputStream`类来分别进行**字节流**的输入输出操作

~~~java
            // 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
            InputStream is = context.getAssets().open(DB_NAME);
            // 输出流,在指定路径下生成db文件
            OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
            // 文件写入
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) 
                os.write(buffer, 0, length);
            // 关闭文件流
            os.flush();os.close();is.close();
~~~

代码也比较简单，这样我们就实现了文件的写入覆盖，就可以在**本地编辑好数据库**之后，直接将数据库跑在安卓的机器上啦



### 2.6 强制下线以及网络监听（广播机制）

> **涉及Java代码**：
>
> - ForceOfflineReceiver.java
> - BaseActivity.java
> - ActivityCollector.java
>
> **相关配置文件：**
>
> 无直接相关的，只需要调用上面的Java类代码即可自动触发功能

**退出登录**以及**网络中断**的实现效果：

![image-20230620102313869](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620102313869.png)

#### 2.6.1 注册广播器：

首先要对广播器Receiver进行**注册**

在BaseActivity当中进行注册，然后所有有需求执行登出操作的界面都可以继承这个类作为基类，这样就会统一收到广播了

注册的代码如下：

**BaseActivity：**

~~~java
@Override
protected void onResume() {
    //动态注册广播器
    super.onResume();
    IntentFilter filter = new IntentFilter();
    //增加需要监听的内容
    filter.addAction("com.example.myyuemiao.FORCE_OFFLINE"); //监听强制下线广播
    filter.addAction("android.net.conn.CONNECTIVITY_CHANGE"); //监听网络连接广播
    forceOffLineReceiver = new ForceOffLineReceiver();
    //注册监听，这样ForceOfflineReceiver就可以接收到所有FORCE——OFFLINE的广播
    registerReceiver(forceOffLineReceiver,filter);
}
@Override
protected void onPause() {
    super.onPause();
    if(forceOffLineReceiver != null)
        unregisterReceiver(forceOffLineReceiver); //最后取消注册
}
~~~

这里增加了两条需要监听的内容，分别是强制下线广播和网络链接广播，这样就很容易地实现了注册

最后不要忘了在onPause或者onDestroy的时候进行取消注册，不然可能会引发一些后果。

#### 2.6.2 实现监听触发事件

接下来就是要到ForceOffLineReceiver这个类当中去实现具体的**监听触发**事件了。

具体类实现：

**ForceOffLineReceiver**：

```java
public class ForceOffLineReceiver extends BroadcastReceiver {
    //该监听器包括了网络监听何强制下线监听
    @Override
    public void onReceive(Context context, Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);//构建对话框
        builder.setTitle("警告");
        String action = intent.getAction();
        if (action.equals("com.example.myyuemiao.FORCE_OFFLINE")) {
            // Handle the FORCE_OFFLINE broadcast
        }
        else if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            // Handle the CONNECTIVITY_CHANGE broadcast
        }
        builder.show();//令该对话框可视化
    }
```

首先该监听器要继承`BroadcastReceiver`，然后去重载它的`onReceive（）`方法，

该方法在收到之前添加的监听事件之后就会触发，我们需要做一个if判断它是哪个类型的事件触发了即可

---

## 3. 新功能以及亮点

>  这一部分介绍的是过去实验未实现的功能，以及一些APP没有的功能的亮点介绍
>
> ### 新功能介绍
>
> 1. 轮播图（ViewPager + Thread线程轮询）
> 2. 位置服务（位置服务）
> 3. 受种者信息数据库
> 4. 二维码扫码（跨程序共享数据，zxing框架）

### 3.0 亮点介绍

1. 轮播图的实现，虽然是之前用过的ViewPager，但是用了同样的工具，展现的是完全不一样的功能，让主页**更加动态和内容更加丰富**

2. 百度地图实现搜索功能：

   这里是书上也并没有的教程，该APP中不仅实现了基础的当前定位功能，还可以对周围建筑地址**进行搜索**，并且返回一个列表，点击提供的社康，门诊的按键也可以快速地查找周围的门诊或者医院

   ![image-20230620004141771](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620004141771.png)

3. 受种者信息操作：（完善原APP功能）

   这里在原本的APP里面只能新增受种者信息，但是发现无法进行删除和修改

   于是将它的增删改查都完善了，并且加上了**绑定当前用户**的功能

   ![image-20230620004532814](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620004532814.png)

4. 最后是二维码扫描：

   这里不仅调用了摄像头的接口，并且也实现了**二维码的扫描匹配**，可以扫描或者使用相册里面的图片

   也可以根据输入的信息生成自己的团体码，或者输入别人的来加入团体

   ![联想截图_20230619173712](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230619173712.png)

   



### 3.1 轮播图

参考博客：

[(158条消息) Android开发-轮播图的详细实现_android31实现轮播图_GYongJia的博客-CSDN博客](https://blog.csdn.net/GYongJia/article/details/89645378)

实现过程中若遇到问题可以到我最下面的第四部分看看有没有同样的问题出现

> 实现效果：
>
> ![联想截图_20230619232927](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230619232927.png)
>
> - xml文件：
>   - fragment_slide_show.xml （对应布局文件）
>   - pager_img.xml （values当中的资源文件）
>   - dot.xml（drawable下的描点文件，用以表示轮播的当前位置）
>   - 五张轮播图照（png）
>   - 两种点的照片（分别表示选中和未选中）
> - java文件：
>   - SlideShowFragment.java（碎片）
>   - LoopViewAdapter.class（该类直接放在上面的碎片java文件当中）

#### 3.1.1 xml文件配置

这里用到了三个xml文件

主要看 `fragment_slide_show.xml` ，该文件使用ViewPager实现轮播图；

其实原理是和之前的新闻列表切换是一样的，只不过这次没有了TabLayout

**fragment_slide_show.xml**

```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    .....
    tools:context=".homepage.fragment.SlideShowFragment">
    <!--存放图片的ViewPager-->
    <androidx.viewpager.widget.ViewPager
        .......
        android:id="@+id/loopviewpager"/>
    <LinearLayout
        .....    >
        <!-- 标题-->
        <TextView
            ....../>
        <!-- 小圆点-->
        <LinearLayout
            android:id="@+id/ll_dots_loop"..../>
    </LinearLayout>
</FrameLayout>
```

可以看到这里的布局比较简单，上面是用一个ViewPager，实现轮播图的效果

然后用圆点和标题方便标识不同的页面

#### 3.1.2 Java文件

所有相关的代码都在`SlideShowFragment.java`下

**开启线程Thread**

这里主要讲下轮播图如何实现定时滚动

关键代码在于开启一个新的线程来进行轮询，这样就可以在不影响主线程的情况下

做到定时轮询，然后同时也可以实现其他的功能了。

**SlideShowFragment.java**

```java
// 开启轮询 
new Thread(){
    public void run(){
        while(isRunning){
            try{
                Thread.sleep(5000); //间隔5s换一张图
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //下一条
            if(!isRunning) //若退出登录，则活动停止，线程终止
                break;
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                } }); } }
}.start();
```



### 3.2 地图功能实现

这个功能可以说是工程量较大的一个内容了；

需要导入百度的SDK，还要获取API KEY

**主要参考：**

[(159条消息) Android集成百度地图实现搜索及列表展示_android 模仿百度地图搜索_“嗯哈的博客-CSDN博客](https://blog.csdn.net/weixin_43117800/article/details/116236948)

参考该博客的gitee的源码，写的挺完善的。

> 实现效果以及功能：
>
> - 可以点击地图的不同位置，会自动在下面列表显示相关的地址信息
>
> - 右上角的logo可以回到我们当前所在的位置
> - 也可以直接搜索我们想要的地址信息
> - 双击或者双指滑动可以放大地图等等
>
> ![image-20230619175026770](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230619175026770.png)
>
> xml配置文件：
>
> - activity_clinic_map.xml 、location_item.xml （layout文件夹）
> - gray_bk_15.xml 、seach_bg.xml（drawable文件夹）
>
> java 文件配置：
>
> - ClinicMapActivity（地图页面）
> - ClinicListAdapter（搜索结果列表适配器）
> - BaiduMapUtils（提供位置信息`LocatioClient`的初始化以及计算距离等相关函数）
>
> 外部导入包以及API KEY获取：
>
> 这些网上都有很多教程，这里就不赘述了

#### 3.2.1 xml文件配置

**activity_clinic_map.xml** 

首先是地图的页面，这里用到了一个关键的控件`MapView`，是百度的SDK提供的

```xml
<com.baidu.mapapi.map.MapView
    android:id="@+id/mapview"
    android:clickable="true" />
```

**AndroidManifest.xml**

这个文件的配置很重要，主要配置两个地方

- 权限配置

  ```xml
  <!-- 这个权限用于进行网络定位 -->
  <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> <!-- 这个权限用于访问GPS定位 -->
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> <!-- 用于访问wifi网络信息，wifi信息会用于进行网络定位 -->
  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /> <!-- 这个权限用于获取wifi的获取权限，wifi信息会用来进行网络定位 -->
  ...........
  ```

  这里涉及到很多权限，具体完整的可以直接去看源码

- API KEY配置

  ```xml
  <application android:name=".MyApplication">
      <service
          android:name="com.baidu.location.f"
          android:enabled="true"
          android:process=":remote" />
      <meta-data
          android:name="com.baidu.lbsapi.API_KEY"
          android:value="这里换成你自己API KEY的值" />
  </application>
  ```


#### 3.2.2 java相关文件

- 在`ClinicMapAcitiity`和`BaiduMapUtils`当中的很多代码都是以配置为主

具体的配置代码在官网也可以查询到

例如初始化**当前定位信息**，调用的是`initLocation()`

**ClinicMapActivity.java**

```java
private void initLocation() {
    //client的各种配置信息
    mLocationClient = BaiduMapUtils.getLocationClient(ClinicMapActivity.this);
    //添加监听器
    MyLocationListener myLocationListener = new MyLocationListener();
    mLocationClient.registerLocationListener(myLocationListener);
    mLocationClient.start();
}
```

在MyLocationListener当中会进行调用回调函数`onReceiveLocation()`

```java
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
			.....
            showMap(native_latitude, native_longitude);
//            mLocationClient.stop();
        }
    }
```

这个函数的调用的是时候我是遇到了一点问题的，我放到后面讲

这里的**showMap**函数可以实现定位到想要去的位置，只要**输入经度纬度**即可

实现效果如下：

![联想截图_20230620003114](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230620003114.png)

实现的效果如上图所示，可以自动移动地图，并且在当前经纬度**标上一个记号**



- 而在`ClinicListAdapter`则是对获取的poiInfo信息进行处理，poiInfo是定位后返回的list列表的信息

最终渲染出来的**搜索的列表**如下：

![image-20230620102537740](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620102537740.png)

而实现它的关键就是使用百度地图的POI搜索，PoiInfo是什么呢，简单介绍一下：

> POI（Point of Interest），即“兴趣点”。在地理信息系统中，一个POI可以是一栋房子、一个景点、一个邮筒或者一个公交站等。
> 百度地图SDK提供三种类型 的POI检索：城市内检索、周边检索和区域检索（即矩形区域检索）。下面分别对三种POI检索服务的使用方法作说明。

```json
PoiInfo: name = 五里店; uid = fe29f769d4d79f839d9d682e; address = 江北区; province = 重庆市; city = 重庆市; area = 江北区; street_id = fe29f769d4d79f839d9d682e; phoneNum = ; postCode = null; detail = 1; location = latitude: 29.584075, longitude: 106.562899; hasCaterDetails = false; isPano = false; tag = null; poiDetailInfo = PoiDetailInfo: ......
```

上面是PoiInfo的部分信息，可以看到会返回非常详细的信息，支撑我们做出各种数据处理

在`ClinicMapActivity`当中获取到poi数据后,传入到`ClinicListAdapter`进行数据渲染

**ClinicMapActivity**

在代码当中注册Poi搜索的**监听器**,然后调用`mClinicListAdapter.addAll(native_latitude, native_longitude, allPoi);`传输数据

```java
private void initpoiSearch() {
    mPoiSearch = PoiSearch.newInstance();
    mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {       @Override
        public void onGetPoiResult(PoiResult poiResult) {
            List<PoiInfo> allPoi = poiResult.getAllPoi();
            if (allPoi != null && allPoi.size() != 0) {
                mLocation = allPoi.get(0);
                mClinicListAdapter.addAll(native_latitude, native_longitude, allPoi);
            } else {mClinicListAdapter.notifyDataSetChanged();}
        }
```



### 3.3  二维码生成以及扫描

> 参考博客（该功能的项目文件体量是目前所有功能当中最大的）：
>
> [(159条消息) zxing开源库的基本使用_安辉就是我的博客-CSDN博客](https://blog.csdn.net/lowprofile_coding/article/details/83386050)
>
> 对应Github源码地址
>
> [ansen666/ZxingTest (github.com)](https://github.com/ansen666/ZxingTest)
>
> 实现效果：
>
> 通过输入任意数字，英文，中文都可以生成属于自己的二维码，也可以点击扫描别人的二维码从而获取团体码数字内容
>
> 可以长按自己的二维码进行**识别或者保存**
>
> ![联想截图_20230619173712](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/联想截图_20230619173712.png)
>
> 外部导入依赖（Google的zxing库）：
>
> ```gradle
> implementation 'com.google.zxing:core:3.3.3'
> ```
>
> **xml文件**：
>
> - 源项目对应的xml配置
>
>   ![image-20230620005303301](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620005303301.png)
>
>   这里的activity_main移植之后换成自己定义的活动页面就好了
>
> - 还有一些colors、themes、strings等文件需要替换，但是这些也可以不用它的
>
>   不是必需的
>
> **java文件**：
>
> 对应的Java文件也是挺多的，分成了不同的模块
>
> ![image-20230620005557261](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620005557261.png)
>
> 大概有十个文件左右，除了两个activity，其他全是各种功能类

#### 3.3.1 xml文件配置

首先是AndroidManifest的权限配置：

~~~xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.autofocus" />

~~~

**activity_scan.xml**

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              ..>
    <include layout="@layout/title_top_white"/>
    <FrameLayout  ......>
        <SurfaceView
            android:id="@+id/scan_activity_preview"
            android:keepScreenOn="true" .../>

        <com.example.yuemiaoapp.teambookpage.view.ScannerView
            android:id="@+id/scan_activity_mask"
            .... />
......
</LinearLayout>
```

这里导入了两个比较特别的控件，`SurfaceView`和`ScannerView`，用来实现扫描的效果

#### 3.3.2 Java源码

1. **扫描识别二维码：**

先介绍一下点击扫描二维码的功能，使用intent来开启一个`ScanActivity`，这个Activity是我已经封装好的，对应UI实现如下：

![联想截图_20230618215334](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230618215334.png)

ScanActivity里面处理了扫描二维码的整个流程，扫描成功后会把扫描结果返回。ScanActivity类的代码有点多，就不贴出来了，有兴趣的自己看源码。

2. **TeamBookActivity**

```java
case R.id.btn_scanning://扫描
    Intent intent = new Intent(TeamBookActivity.this,ScanActivity.class);
    startActivityForResult(intent,SCAN_REQUEST_CODE);
    break;
```

然后在重写Activity类的onActivityResult方法来处理收到的数据：

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);
    if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
        String input = intent.getStringExtra(ScanActivity.INTENT_EXTRA_RESULT);
        showToast("扫描结果:"+input);
    }
}

```

3. **从相册选择二维码图片**

![联想截图_20230620101548](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230620101548.png)

代码同样在`teambookactivity`下，启动系统相册，并且从中选择图片

~~~java
Intent innerIntent = new Intent(Intent.ACTION_PICK,
MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
startActivityForResult(wrapperIntent, SELECT_IMAGE_REQUEST_CODE);
~~~

然后同样是在`onActivityResult`当中处理选择的图片的内容，调用BitmapUtil.parseQRcode方法解析二维码图片。

~~~java
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);
    if(requestCode==SELECT_IMAGE_REQUEST_CODE){//从图库选择图片
		......
        if (cursor.moveToFirst()) {
            ......
            String result= BitmapUtil.parseQRcode(photoPath);
            if (!TextUtils.isEmpty(result)) {
                showToast("从图库选择的图片识别结果:"+result);
            } else {
                showToast("从图库选择的图片不是二维码图片");
            }
        }
        cursor.close();
    }
}
~~~

`parseQRcode()`方法是将文件路径读取成Bitmap格式的数据进行压缩

4. **生成二维码图片**

实现效果:

![image-20230620103614531](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620103614531.png)

生成二维码图片调用CreateQRBitmp.createQRCodeBitmap方法生成，这个方法是我们自己封装的，需要传入两个参数:

参数1:  图片内容、   参数2:   二维码图片最中间显示的logo(Bitmap对象)。

~~~java
String contentString = etInput.getText().toString().trim();
if(TextUtils.isEmpty(contentString)){
    showToast("请输入二维码内容");
    return ;
}
Bitmap portrait = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//两个方法，一个不传大小，使用默认
qrCodeBitmap = CreateQRBitmp.createQRCodeBitmap(contentString, portrait);
ivQrImage.setImageBitmap(qrCodeBitmap);
~~~

至此扫描二维码的主要功能就介绍完了.



### 3.4 受种者记录操作

> 实现效果:
>
> 可以在受种者记录处查看当前的记录信息,可以点击右下角的＋号进行新增
>
> 也可以点击进去具体的记录信息查看,同时具体的信息界面提供了修改以及绑定用户的方法
>
> ![联想截图_20230619175419](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230619175419.png)
>
> xml文件:
>
> - activity_inoculate_edit.xml　（编辑或者新增１具体信息）
> - activity_inoculate_info_page.xml　（展示具体信息）
> - activity_inoculate_list.xml （受种者信息的列表）
> - activity_person_page.xml (入口)
>
> Java文件(personpage软件包下):
>
> ![image-20230620110133117](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620110133117.png)

**功能介绍:**

**展现受种者信息:**

对应数据表(inoculateinfo):

![image-20230620110444203](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230620110444203.png)

看到最右边的字段是user_id作为外键, 区分不同用户下的受种者记录,防止冲突

**InoculateListAdapter** (该类在InoculateListActivity文件当中)

```java
public InoculateListAdapter(Activity activity){
    this.activity = activity;
    //获取当前用户的受种者记录
    inoculateInfoList = LitePal.where("user_id = ?",String.valueOf(userInfo.getId()))
            .find(InoculateInfo.class);
}
```

上面在初始化Adapter的时候,通过`LitePal.where()`来筛选不同用户对应的受种者信息.



## 4. 遇到的问题

### 4.1 设置NavigationView时控件无法直接引用

nav_header无法直接被`activity.findViewById()`找到

由于该控件不是在**activity_main.xml**当中，只是被引用了

![联想截图_20230518134252](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518134252.png)

所以无法直接找到，因此要用如下代码获得

~~~java
        
NavigationView mNavigationView = activity.findViewById(R.id.nav_view);
View headerView = mNavigationView.getHeaderView(0);
~~~

也就是先获得NavigationView，再获取它当中的**headerVIew**

### 4.2  轮播图遇到的问题：

- 在退出登录的时候，Thread线程没有结束，但是活动已经销毁了，所以会导致报错

  **解决方法**：退出登录的时候要用标志位进行判断

### 4.3 **百度地图遇到的问题：**

- debug.keystore报错不能运行 说是invalid

  解决：删除debug.keystore 等它自动重新创建

- butterknife工具无法使用

  解决：要把gradle版本调低

- smartrefresh包无法正常导入

  解决：新版的gradle里面没有这个库

  [(159条消息) Failed to resolve: com.scwang.smartrefresh:SmartRefreshLayout:1.1.2_hudawei996的博客-CSDN博客](https://blog.csdn.net/Goals1989/article/details/125724756)

  对应上面博客，将setting.gradle当中的文件修改一下，加上jcenter

  ![img](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/48033d7ff4a64807b0cc8ce7ec026501.png)

- 外部导入的包发现和support-v4有冲突

  解决：在`gradle.properties`加上

  ~~~properties
  android.enableJetifier=true
  ~~~

- 模拟器运行的时候无法执行LocationListener监听器`onRecieveLocation`函数，但是用**真机**又可以、

  也就是说模拟器运行状态下无法实现**当前位置的定位**
  
- 报错`has been stopped because you do not aaree with the orivacy compliance policy,Please recheck the setAoreePriyacy intarfao`

  网上都说是没有设置那个`setAgreePrivacy`为True，但是设置了也不行

### 4.4 数据库的框架litepal无法直接导入

关于这个问题和那个`smartrefresh`开源包无法导入是一样的，当前gradle不支持了

所以还是要将`settng.gradle` 的两个Management 都加上`jcenter（）`

## 5. 总结

xxxxxxxxxxxxxxxxxxxxxx不给你看
