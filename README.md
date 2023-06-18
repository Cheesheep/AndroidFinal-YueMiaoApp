# Android studio实现校园APP（完整步骤）

[TOC]

> 开发语言：Java
>
> 开发工具：Android Studio
>
> 用到的一些控件，功能：
>
> Tablayout，ViewPager（滑动切换菜单功能），Navigation，ToolBar
>
> **需要的外部包**：
>
> ```java
>     //新增的四个依赖，用于Tablayout，viewpager，recyclerView布局，以及数据库litepal
>        implementation 'androidx.legacy:legacy-support-v4:1.0.0'
>        implementation 'com.google.android.material:material:1.5.0'
>        implementation 'androidx.recyclerview:recyclerview:1.2.1'
>        implementation files('libs\\litepal-2.0.0-src.jar')
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
> 2. 最后一个litepal的引入是本地下载好的依赖
>
>    这里尝试过直接引入`implementation 'org.litepal.guolindev:core:3.2.0'`或者是`implementation 'org.litepal.android:core:3.2.0'`
>
>    但是发现在Java，manifest文件当中根本找不到喝这个库相关的内容
>
>    于是就直接到github上面下载jar包并且导入了
>
>    给出下载地址
>
>    https://github.com/guolindev/LitePal#latest-downloads/



## 1. 效果展示

> 实现功能如下：
>
> - 自适应平板和手机的**碎片化**布局（Fragment）
> - 用户登录（数据持久化）：**数据库文件存储和读取**账号密码
> - 强制登出（广播机制）
> - 提示网络中断（广播机制）
> - 记住密码功能（数据持久化）：使用**SharedPreference**
> - 点击页面打开嵌入式Web内容（使用WebView）以及跳转到**浏览器**
> - 双页模式使用**侧边栏**放置信息条（NavigationVIew）
> - 右侧单页使用Tab实现切换
> - 使用RecyclerView实现网格布局（右边碎片网格菜单）
> - 左边碎片的网格菜单布局GridLayout

- 首先一打开APP的页面就是登录页面。

  ![联想截图_20230517232119(1)](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230517232119(1).png)

  可以选中**记住密码**，下次就可以不用输入了

  ![联想截图_20230517233602(1)](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230517233602(1).png)

  平板模式如图所示，自适应到屏幕中间

- 登录后进入到首页，这里给出的平板和手机两种布局，稍有不同

  ![联想截图_20230517232617(1)](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230517232617(1).png)

  这里最上面导航栏左边是**个人信息的侧边栏**，最右边的按钮是**退出登录**的按钮。接着是**网格菜单**，可以点击进入不同的页面

  最下面是可以左右切换或者上下滚动的**学校新闻列表**

  点击网格菜单右下角的`更多服务`的按钮，会展示更多的**网格菜单**内容

  ![联想截图_20230517233152(1)](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230517233152(1).png)

  平板打开的话页面如下：

  ![联想截图_20230517233423(1)](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230517233423(1).png)

  看到这里左边已经没有了网格菜单，只剩下新闻列表的内容

  然后右边列出了刚刚给出的更多新闻的菜单，这里实际上就是将**两个碎片**拼在了一起

- 接着打开侧边栏，展示了部分用户信息以及一些导航栏入口

  ![联想截图_20230517233637(1)](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230517233637(1).png)

  手机的话也是类似的，最下方也有一个用于登出的**注销**按钮

  点击**用户信息**或者用户的**头像**可以进入个人信息页面

  ![联想截图_20230518114857](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518114857.png)
  
  在用户信息页面当中会展示更多的个人信息

- 尝试点击进入一个菜单的详情页面或者通知，会通过WebView将链接当中的网址内容展现出来

  ![联想截图_20230517232807(1)](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230517232807(1).png)

  如图为手机下打开《计软主页》内容，点击右上角可以选择跳转到浏览器打开该链接

- 最后做了一个**网络中断检测**

  ![联想截图_20230518121747](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518121747.png)

  网络中断的时候会有弹窗弹出，但不会强制下线，不过在进入到每一个新的页面的时候，若还是网络中断，则也会弹出该警告

  



---



## 2. 具体实现

> 首先看一下Java类的文件布局
>
> ![联想截图_20230518125521](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518125521.png)
>
> 主要分成三个部分
>
> 这个BaseActivity是其他所有类的基类，通过该类可以实现任意一个页面强制下线的功能
>
> 功能类大致介绍一下：
>
> - ActivityCollector：用于管理增加或回收所有活动
> - DatabaseUtil：对存在assets中的.db数据库文件做读取存储实现
> - ForceOffLineReceiver：实现强制下线以及网络状态监听
> - NavigationUtil：对侧滑栏的控件绑定数据，视图等进行动态设置
> - NewsFragmentPagerAdapter：对viewPager显示通知内容进行适配
> - RightGridMenuAdapter：对右侧碎片的RecyclerView内容进行适配





### 2.1 顶部栏ToolBar以及侧滑栏Navigation

> 顶部菜单栏：
>
> ![image-20230518130742345](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230518130742345.png)
>
> 侧滑栏：
>
> ![联想截图_20230518130831](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518130831.png)
>
> xml文件：
>
> - activity_main.xml ：主活动页面
> - toolbar_menu：菜单栏图标（右侧的分享和个人页面的logo）
> - nav_header,xml ：侧滑栏的头部信息（头像，昵称，联系方式）
> - left_drawer.xml：侧滑栏的菜单内容
>
> Java文件：
>
> - MainActivity
> - NavigationUtil
>
> 

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

![联想截图_20230518131127](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518131127.png)

**themes.xml**

![image-20230422164201720](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230422164201720.png)

看到最右边设置了noActionBar，这样才可以让toolbar正常显示

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
        android:id="@+id/tool_msg"
        android:icon="@drawable/share"
        android:orderInCategory="80"
        android:title="edit"
        app:showAsAction="ifRoom|withText" />
    <item
        android:id="@+id/tool_user"
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

添加NavigationView组件

（要使用该组件需要从外部导入design包，在前面有提到导入的具体的包）

将该组件添加到toolbar所在的活动的页面当中，这里就是activity_main.xml

**activity_main.xml部分代码**

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.drawerlayout.widget.DrawerLayout
    ...............>

    --------
    这里是该页面的其他布局文件，可以将整个布局文件代码放进去也是可以的
    
    --------
    
<!--左侧导航菜单-->
<com.google.android.material.navigation.NavigationView
	..............
    android:layout_gravity="start"
    android:background="@color/colorPrimary"
    app:headerLayout="@layout/nav_header"
    app:menu="@menu/left_drawer" />
</androidx.drawerlayout.widget.DrawerLayout>
```

这个菜单栏由于是侧滑出现的，所以放在整个布局的最外面，并且需要用`DrawerLayout`包裹

注意看下面两个app的属性`app:headerLayout`和`app:menu`

除此之外需要新建两个文件，一个是头部的代码，一个是菜单的部分

**nav_header.xml**

代码以及预览如图

![联想截图_20230518132647](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518132647.png)

看下左边，这里用了帧布局的方法嵌套一个`ImageView`和`RelativeLayout`，ImageView这样就可以作为背景图片，使得导航栏头部更加美观

**left_drawer.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu 
     xmlns:app=".......">
        <item
            android:id="@+id/navigation_item_user" />
        <item
            android:id="@+id/navigation_item_setting"/>
        <item
            android:id="@+id/navigation_item_about"/>
        <item
            android:id="@+id/navigation_item_logout"/>
</menu>
```

这个是菜单栏的页面，预览的效果和最终实现的效果是**有差异的**，不需要以预览的那个效果为准。

将nav_header和left_drawer结合之后，效果图片在上面有显示。

#### 2.1.4 Java逻辑代码实现

ToolBar的初始化代码都在**MainActivity**当中。

侧滑栏的代码为了减少MainActivity的冗余，将其单独分离出了一个功能类NavigationUtil

程序入口在onCreate当中实现

**MainActivity**

~~~java
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置顶部菜单栏的按钮，监听事件等
        initToolBarView();
        NavigationUtil navigationUtil = new NavigationUtil(this,myToolbar,drawerLayout);
        navigationUtil.initMainNavigation();
    }
~~~

先调用`initToolBarView（）`进行初始化

接着new一个新的Util类，并且将需要用到的控件传参进去，调用`initMainNavigation（）`

这里稍微说一下NavigationView的设置的时候一些注意事项

- nav_header无法直接被`activity.findViewById()`找到

  由于该控件不是在**activity_main.xml**当中，只是被引用了

  ![联想截图_20230518134252](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518134252.png)

  所以无法直接找到，因此要用如下代码获得

  ~~~java
          
  NavigationView mNavigationView = activity.findViewById(R.id.nav_view);
  View headerView = mNavigationView.getHeaderView(0);
  ~~~

  也就是先获得NavigationView，再获取它当中的**headerVIew**

- 用户的信息展现是动态的，这里的数据是存储在**数据库**当中的

  ~~~java
          nickName.setText(LoginActivity.userInfo.getNickname());
          grade.setText(LoginActivity.userInfo.getGrade());
          major.setText(LoginActivity.userInfo.getMajor());
  ~~~

  如上，信息都存储在来自登录活动当中的静态成员变量userInfo，后面会有介绍登录的实现

  不同的用户登录，显示的信息是不一样的

  ![联想截图_20230518150801](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518150801.png)

#### 2.1 5 个人信息页面

最后这里简单地介绍一下个人页面的信息。

可以点击侧滑栏的头像或者《用户信息》都可以跳转到该页面。

![联想截图_20230518114857(1)](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518114857(1).png)

这里放出的是平板的界面，和手机是完全一样的，

只是平板会拉伸，但是信息内容都是左右放置，所以不会有区别

---



### 2. 2平板手机适配（Fragment）

为了实现手机的平板的适配，就需要用到一个非常强大的功能Fragment了，它被称为安卓的第五大组件，

可以实现类似于Activity的效果，同时有具有极为灵活的特性

看一下**activity_main**文件

![image-20230518153955863](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230518153955863.png)

首先这里有两个文件，带有(sw600dp)的是存放在**layout-sw600dp**的文件夹当中

这样当屏幕比较大的时候，就会自动加载（sw600dp）的activity_main文件了

activity_main (sw600dp)

~~~xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.drawerlayout.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android".........><LinearLayout
android:layout_width="match_parent" .......><!--顶部工具栏-->
<androidx.appcompat.widget.Toolbar   ......./><ImageView ............/><LinearLayout.........>
<!--左边碎片-->
<fragment android:id="@+id/main_left_fragment"
    android:name="com.example.yuemiaoapp.fragment.HomepageFragment"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:layout_weight="3" tools:layout="@layout/fragment_main_left"
    tools:ignore="MissingConstraints" />
<!--右边碎片-->
<fragment android:id="@+id/right_frag"
    android:name="com.example.yuemiaoapp.fragment.MoreServiceFragment"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:layout_gravity="center" android:layout_weight="2"
    tools:layout="@layout/fragment_main_right" tools:ignore="MissingConstraints" />
</LinearLayout></LinearLayout><!--左侧导航菜单-->
<com.google.android.material.navigation.NavigationView...... /></androidx.drawerlayout.widget.DrawerLayout>
~~~

这里省略了其他的内容，着重于看两个fragment，这里的**layout_weight**的大小不同，说明它们占据的布局位置也不同。

而在适配手机的activity_main当中，就不需要后面的那个fragment的碎片。





### 2.3 左侧碎片 （MainLeftFragment）

对控件的初始化代码都放在该Fragment类的`onCreateView（）`当中

~~~java
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //获取碎片视图
        view = inflater.inflate(R.layout.fragment_main_left, container, false);
        //设置首页工具栏内容以及样式
        //初始化上方的网格菜单
        initGridMenu();
        //将页面绑定viewPager，进行设置
        initViewPager();
        //初始化layout的设置，例如图标，定位
        initTabLayoutView();
        return view;
~~~



#### 2.3.1 网格菜单（GridLayout）

网格菜单使用了gridLayout，并且在里面 放置了多个图片的内容

~~~xml
<GridLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:columnCount="4"   设置它的最大列
    android:rowCount="2"    设置最大行
    android:layout_gravity="center"
    android:orientation="horizontal"
    >
~~~

实现效果如图

![联想截图_20230518155931](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518155931.png)

#### 2.3.2 通知列表（TabLayout + ViewPager + Fragment）

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
>![联想截图_20230518160221](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518160221.png)
>
>**涉及配置xml文件**：
>
>- fragment_main_left.xml （左侧碎片布局）
>- main_grid_menu.xml （网格菜单）
>- selected.xml （选中菜单的颜色设置）
>- themes.xml  （设置菜单的文件大小）
>
>**Java代码文件**：
>
>- MainLeftFragment （整体左侧碎片）
>- NewsFragmentPagerAdapter（用于切换列表的适配器）
>- NotificationFragment（通知列表碎片）

##### 界面配置文件

给出整体的左侧碎片简略xml代码

**fragment_main_left.xml**

~~~xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	.................>
    <!--顶部网格菜单-->
    <include layout="@layout/main_grid_menu"/>

    <!--顶部导航栏，切换选项卡-->
    <com.google.android.material.tabs.TabLayout
        app:tabBackground="@drawable/selected"
        app:tabTextAppearance="@style/MyTabLayoutTextAppearance"
		................/>
    <!--用于实现左右滑动效果-->
    <androidx.viewpager.widget.ViewPager
        android:id="@+id/viewPager"
		........./>
    <ImageView android:id="@+id/more_page_bt"/>
</LinearLayout>

~~~

中间的两个`ViewPager`和`TabLayout`就是我们用来实现通知列表的控件

初始化这几个控件的方法，具体源码去查看仓库代码

~~~java
        //将页面绑定viewPager，进行设置
        initViewPager();
        //初始化layout的设置，例如图标，定位
        initTabLayoutView();
~~~

每个通知List的文章布局：

![image-20230518171636001](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230518171636001.png)

对应的数据库内容：

![image-20230518171829841](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230518171829841.png)

这里最右侧的”标签“是用于显示不同tab下的文章内容

获取通知列表数据的方法如下

**NotifictaionFragment.java**

~~~java
    private void initData() {
        //根据label标签条件查询处相应的文章
        mList = LitePal.where("label = ?",label).find(Notification.class);
    }
~~~

根据where方法获取对应label下的内容，这样就可以将全部通知信息放在**一张表**下面



**注意事项！：**

**其他xml文件的添加**

这里说几个tablayout和viewPager要导入的一些其他的简短的xml文件

![image-20230423002236907](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230423002236907.png)

在`themes.xml`（或styles.xml）文件当中要添加`style name = MyTabLayoutTextAppearance`的样式

```xml
<!--    TabLayout的文本大小-->
    <style name="MyTabLayoutTextAppearance" parent="TextAppearance.AppCompat.Widget.ActionBar.Title">
        <item name="android:textSize">18sp</item>
        <item name="android:textColor">@color/white</item>
    </style>
```

还有在drawable文件下的 `selected.xml`，用于展示被选中下菜单的颜色

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_selected="true" android:drawable="@color/TabBackGround"/>
    <item android:drawable="@color/colorAccent"/>
</selector>
```





### 2.4 文章详情页面（WebView）

> **xml文件**：
>
> activity_news_article_content.xml
>
> **java文件**：
>
> NewsArticleContentActivity

**xml文件内容**：

**activity_news_article_content.xml**

这里就用线性布局放置了一个WebView，比较简单

~~~xml
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">
            <WebView
                android:id="@+id/news_webView"
                android:layout_width="match_parent"
                android:layout_height="match_parent"/>
        </LinearLayout>
~~~



这个比较简单，文章或者是菜单什么点击后都会触发事件并且跳转到这个活动，

跳转过来的时候往往会带有一个标题和一个链接url，然后就可以用xml文件里面的WebView显示出来

例如在左侧碎片最下面的一个图标可以跳转到公文通

![联想截图_20230518162233](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/%E8%81%94%E6%83%B3%E6%88%AA%E5%9B%BE_20230518162233.png)



### 2.5 登录界面

上面讲的很多内容都是上一个实验的，而登录界面则涉及到了新的知识，广播机制以及数据持久化存储

> **xml文件**：
>
> activity_login.xml
>
> **登录账号**：
>
> 账号：2020111051  密码：8888
>
> 账号：admin 密码：1111
>
> **java 相关文件**：
>
> - LoginActivity
> - BaseActivity
>
> **登录界面**：
>
> ![联想截图_20230517233602(1)](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/联想截图_20230517233602(1).png)

这里的布局文件只有一个，就不详细介绍了

只说一下用了哪些不太常见的控件

- editText：输入框，可通过`android:theme`设置颜色风格
- CheckBox：勾选框

#### 2.5.1  用户信息校对

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
                    { 
                        //密码正确后的逻辑代码
                    }
                }
~~~

看到上面的代码通过

~~~java
                userInfoList = LitePal.findAll(UserInfo.class);//获取密码数据库信息

~~~

这一行来获取数据库中该表的所有数据

该表的内容如下

![image-20230518171531080](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230518171531080.png)



#### 2.5.2 记录用户数据（SharedPreference）

为了让用户登录更加方便，在登录页面当中，**记住密码**这个功能是必不可少的

而用SharedPreference可以很方便地实现这个功能

代码也很简单，

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



### 2.6 数据库实现 （LitePal）

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

#### 2.6.1 导入依赖以及配置

这里我学习了《第一行代码》当中给出的LitePal的库的使用并且进行了导入

不过这里用给出的导入方法或者网上的都不行，于是只能到github直接将**Jar包**下载下来了。

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
      <dbname value="SchoolDemo"></dbname>
      <version value="3" ></version>
      <list>
          <mapping class="com.example.yuemiaoapp.schobject.Notification"></mapping>
          <mapping class="com.example.yuemiaoapp.schobject.UserInfo"></mapping>
          <mapping class="com.example.yuemiaoapp.schobject.Card"></mapping>
      </list>
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

#### 2.6.2 存储提前准备好的数据文件

由于考虑到每次到一台新的机器上面，都会创建一个新的数据库db，因为它是不存在本地的，因此我们要去读取它的文件

为了避免在Java当中还要手动写代码去导入数据到数据库当中，而且数据也不方便修改

于是可以采用将**存在安卓资源文件中的db数据库** 写入 到android手机存储db数据库的位置当中，这样就实现了文件的覆盖，可以使用预先写好数据的数据库了！

这就涉及到文件的读写操作了，我们将数据库文件存放在assets目录下面

![image-20230518175126413](https://gitee.com/cheesheep/typora-photo-bed/raw/master/Timg/image-20230518175126413.png)

名字也要和我们要生成的安卓手机上的数据库**文件名字一致**，接着编写代码进行IO操作

**DatabaseUtil.java**

首先在Java当中获取存取的路径名称

~~~java
// com.example.myschool 是程序的包名，请根据自己的程序调整
// /data/data/com.example.myschool/databases目录是准备放 SQLite 数据库的地方，也是 Android 程序默认的数据库存储目录
// 数据库名为 SchoolDemo.db
String DB_PATH = "/data/data/" + context.getPackageName() +"/databases/";
String DB_NAME = "SchoolDemo.db";
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
            os.flush();
            os.close();
            is.close();
~~~

代码也比较简单，这样我们就实现了文件的写入覆盖，就可以在**本地编辑好数据库**之后，直接将数据库跑在安卓的机器上啦



### 2.7 强制下线以及网络监听（广播机制）

> **涉及Java代码**：
>
> - ForceOfflineReceiver.java
> - BaseActivity.java
> - ActivityCollector.java
>
> 相关配置文件：
>
> 无直接相关的，只需要调用上面的Java类代码即可自动触发功能



#### 2.7.1 注册广播器：

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
        filter.addAction("com.example.myschool.FORCE_OFFLINE"); //监听强制下线广播
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

#### 2.7.2 实现监听触发事件

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
        if (action.equals("com.example.myschool.FORCE_OFFLINE")) {
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





## 3. 实验总结

### 3.1 APP亮点

这里列出该APP设计的一些认为比较突出的地方：

- 使用SharedPreference实现记住密码
- 使用安卓的库LitePel实现数据库的账号密码的存取
- 用户信息从数据库中提取，不同的用户动态展现不同的信息
- 使用放在assets文件中已经写好的数据库，可手动编辑
- 碎片化实现平板与手机的适配
- 使用gridLayout网格绘制菜单（与下面对比两种不同的方式）
- recyclerView实现网格菜单



### 3.2. 遇到过的问题

- 数据库无法在新的机器上存储，需要在代码添加进去

  解决方法：

  在本地做好一个数据库文件，然后通过文件字节流读写的方式写入到安卓模拟器上的位置

- ImageView无法通过elevation设置阴影

  使用CardView包裹实现阴影效果



### 3.3 感想心得

本次实验给我的第一感觉就是内容比上次要多了更多，需要做的东西有诸如数据库，广播等等，这也是因为我们学到了更加多的新知识，因此也需要在实验中应用上相关的知识。

虽然这次的内容比较多吧，但是感觉没有上次做起来这么难了，因为上次的是第一次去认真地完成一个APP，从零开始，而这次不仅已经有了上一次实验的经验，有些代码框架和内容也可以沿用上一次的，并且基于上一次做的不足的地方做更好地调整，例如这次我对侧边栏进行了更多地美化，加上了背景图片的嵌入，还有设计了个人信息界面，可以通过侧边栏找到入口进入。另外在页面碎片当中也进行了代码的优化和简化。

当然这一次收获最大的还是数据持久化的学习还有广播机制的应用，使用广播机制就可以实现几乎所有APP都会具有的一个功能，就是登录，而为了存储用户的数据，自然也就需要结合数据持久化的存储，而这次直接学习了数据库的使用，因此在ListView显示每一条文章的数据的时候再也不需要在Java当中手动写入这些数据了，而是可以直接放在数据库db文件当中，而这里用的是安卓提供的SQLite数据库，不仅可以存储用户数据，也可以存储文章数据等等。

不过在学习的同时也越来越发现自己还有很多不会的地方，现在做的实际上对于社会上的安卓工程师，这些只是很简单的东西，例如数据库应该会有更好地方法进行操作，可以利用后台写接口的方式更好地对数据进行处理，总之，仍有很多的地方需要学习。









### 可以改进或者扩展的地方（删掉）

1. 图片没有真正存进数据库里面，以后看看可能可以用Bitmap存储
2. 控件的阴影设置有自定义的方法，也可以用别人的库，还可以用什么`点9图`
3. 数据库以后应该可以采用后台开发的方式，看看能不能用springboot开发
4. 平板正放侧放切屏的时候会闪退
4. 数据库的db文件是否可以一直存在软件当中，还是到新的机器上就会被清除

