[TOC]

# databing概述
DataBinding 是谷歌官方发布的一个框架，顾名思义即为数据绑定，是 MVVM 模式在 Android 上的一种实现，用于降低布局和代码逻辑之间的耦合性，使代码逻辑更加清晰。MVVM 相对于 MVP，其实就是将 Presenter 层替换成了 ViewModel 层。DataBinding 能够省去我们一直以来的 findViewById() 步骤，大量减少 Activity 内的代码，数据能够单向或双向绑定到 layout 文件中，有助于防止内存泄漏，而且能自动进行空检测以避免空指针异常
# 使用
## 1、构建环境
配置你的应用程序使用数据绑定，在应用程序模块 (app module) 的 build.gradle 文件添加数据绑定元素，需要注意的是 Android Studio 的版本需要在 1.3 以上
### 1.java项目
#### 1.app/build.gradle
```
android {
    ....
    //启动dataBinding
    dataBinding {
        enabled = true
    }
}
```
### 2、kotlin项目
Kotlin 里面想用 DataBinding 必须要加上 kotlin-kapt插件，也就是 kotlin 的 apt ，kotlin-kapt 的版本号最好跟着 geadle 一起走
#### 1.Project/build.gradle

```
buildscript {
    ext.kotlin_version = "1.3.72"
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:4.0.0"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        //添加kotlin-gradle插件
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
```
#### 2.app/build.gradle
应用kotlin-kapt
```
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
//应用kotlin-kapt
apply plugin: 'kotlin-kapt'
```
#### 3.Project/gradle.properties

```
kotlin.incremental=false
```

## 2、基础入门
启用 DataBinding 后，这里先来看下如何在布局文件中绑定指定的变量
### 1、xml文件生成data标签
打开布局文件，选中根布局的 ViewGroup，按住 Alt + 回车键，点击 “Convert to data binding layout”，就可以生成 DataBinding 需要的布局规则  
![image](http://note.youdao.com/yws/public/resource/fc6b701fcc36c032670d422e1a3c6889/xmlnote/21E7A7B74134450D84F7A5E1DB236B36/43136)  
生成后的diam如下
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello World!"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
和原始布局的区别在于多出了一个 layout 标签将原布局包裹了起来，data 标签用于声明要用到的变量以及变量类型，要实现 MVVM 的 ViewModel 就需要把数据（Model）与 UI（View）进行绑定，data 标签的作用就像一个桥梁搭建了 View 和 Model 之间的通道
### 2、声明一个modle
这里先来声明一个 Modle
```
package com.example.demo003_databing.model

/**
 * @author: Luuuzi
 * @date: 2020-11-27
 * @description:
 */
data class User(var name:String,var password:String)
```
### 3、data标签设置值
在 data 标签里声明要使用到的变量名、类的全路径。
```
<data>
        <variable
            name="userInfo"
            type="com.example.demo003_databing.model.User" />
    </data>
```
如果 User 类型要多处用到，也可以直接将之 import 进来，这样就不用每次都指明整个包名路径了，而 java.lang.* 包中的类会被自动导入，所以可以直接使用
```
<data>
        <import type="com.example.demo003_databing.model.User"/>
        <variable
            name="userInfo"
            type="User" />
    </data>
```
如果存在 import 的类名相同的情况，可以使用 alias 指定别名
```
    <data>
        <import type="com.example.demo003_databing.model.User" />
        <import
            alias="TempUser"
            type="com.example.demo003_databing.model2.User" />
        <variable
            name="userInfo"
            type="User" />
        <variable
            name="tempUserInfo"
            type="TempUser" />
    </data>
```
### 4、设置将data变量和view联系起来
这里声明了一个 User 类型的变量 userInfo，我们要做的就是使这个变量与两个 TextView 控件挂钩，通过设置 userInfo 的变量值同时使 TextView 显示相应的文本
完整的布局代码如下所示

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="userInfo"
            type="com.example.demo003_databing.model.User" />
    </data>

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity"
        android:orientation="vertical">
        <androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/tv_userName"
            android:layout_width="match_parent"
            android:layout_height="40dp"
            android:text="@{userInfo.name}"/>
        <androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/tv_password"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{userInfo.password}"/>

    </androidx.appcompat.widget.LinearLayoutCompat>
</layout>
```
### 5、代码设置内容
通过 @{userInfo.name} 使 TextView 引用到相关的变量，DataBinding 会将之映射到相应的 getter 方法  
之后可以在 Activity 中通过 DataBindingUtil 设置布局文件，省略原先 Activity 的 setContentView() 方法，并为变量 userInfo 赋值

```
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val user = User("小红", "123456")
        activityMainBinding.userInfo=user
    }
}
```
运行后的效果
![image](http://note.youdao.com/yws/public/resource/fc6b701fcc36c032670d422e1a3c6889/xmlnote/8AA7F790DAE84379AC791580796AD07B/43157)
## 3.其他功能
### 1、设置预览效果
由于 @{userInfo.name}在布局文件中并没有明确的值，所以在预览视图中什么都不会显示，不便于观察文本的大小和字体颜色等属性，此时可以为之设定默认值（文本内容或者是字体大小等属性都适用），默认值将只在预览视图中显示，且默认值不能包含引号
```
android:text="@{userInfo.name,default=预览效果}"
```
此外，也可以通过 ActivityMain2Binding 直接获取到指定 ID 的控件

```
activityMainBinding.tvUserName.text="小明"
```
每个数据绑定布局文件都会生成一个绑定类，ViewDataBinding 的实例名是根据布局文件名来生成，将之改为首字母大写的驼峰命名法来命名，并省略布局文件名包含的下划线。控件的获取方式类似，但首字母小写
```
    <data class="CustomBinding">

    </data>
```
此外，在绑定表达式中会根据需要生成一个名为context的特殊变量，context的值是根View的getContext()方法返回的Context对象，context变量会被具有该名称的显式变量声明所覆盖
### 2、fragment和recyclerview中使用
Databinding 同样是支持在 Fragment 和 RecyclerView 中使用 。例如，可以看 Databinding 在 Fragment 中的使用

```
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentBlankBinding fragmentBlankBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, container, false);
        fragmentBlankBinding.setHint("Hello");
        return fragmentBlankBinding.getRoot();
    }
```
**以上实现数据绑定的方式，每当绑定的变量发生变化的时候，都需要重新向 ViewDataBinding 传递新的变量值才能刷新 UI 。**

## 4、单向数据绑定
实现数据变化时自动更新UI的方式有三种：BaseObservable、ObservableField、ObservableCollection
### 1、Baseobservable
一个纯净的 ViewModel类被更新后，并不会让UI自动更新。而数据绑定后，我们自然会希望数据变更后 UI会即时刷新，Observable就是为此而生的概念，==Observable实现在数据类修改后，同时更新UI==

BaseObservable 提供了 notifyChange() 和 notifyPropertyChanged()两个方法
- notifyChange():刷新所有的值域，
- notifyPropertyChanged():后者则只更新对应BR的 flag，该BR的生成通过注释 @Bindable 生成，可以通过BRnotify特定属性关联的视图

```
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.demo003_databing.BR

/**
 * @author: Luuuzi
 * @date: 2020-11-27
 * @description:创建一个bean类
 */
class Goods : BaseObservable() {
    //Bindable注解只能修饰public的属性
    @Bindable
    public var name: String = ""
        set(value) {
            field = value
            //只更新指定属性
            notifyPropertyChanged(BR.name)
        }

    @set:Bindable
    var details: String = ""
        set(value) {
            field = value
            //更新所有属性
            notifyChange()
        }

    var price: Float = 0f
        set(value) {
            field = value
        }
}
```
build项目后就会生成BR类

```
public class BR {
  public static final int _all = 0;

  public static final int details = 2;

  public static final int goods = 3;

  public static final int name = 4;
}
```

在 setName() 方法中更新的只是name属性，而 setDetails() 方法中更新的是所有属性  

添加两个按钮用于改变 goods 变量的三个属性值。当中涉及的按钮点击事件绑定

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <import type="com.example.demo003_databing.model.Goods"/>
        <import type="com.example.demo003_databing.Test2Activity.ClickHandler"/>
        <variable
            name="goods"
            type="Goods" />
        <variable
            name="click"
            type="ClickHandler" />
    </data>

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/tv_name"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{goods.name}"/>
        <androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/tv_details"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{goods.details}"/>
        <androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/tv_price"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{String.valueOf(goods.price)}"/>
        <androidx.appcompat.widget.AppCompatButton
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="修改name"
            android:onClick="@{()->click.clickName()}"/>
        <androidx.appcompat.widget.AppCompatButton
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="修改details属性(所有属性)"
            android:onClick="@{()->click.clickDetails()}"/>
    </androidx.appcompat.widget.LinearLayoutCompat>
</layout>
```
使用
```
class Test2Activity : AppCompatActivity() {
    lateinit var goods: Goods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest2Binding>(this, R.layout.activity_test2)
        goods = Goods()
        goods.name = "小红"
        goods.details = "漂亮"
        goods.price = 2000f
        dataBindingUtil.goods = goods
        dataBindingUtil.click = ClickHandler()
    }

    //点击事件class
    inner class ClickHandler {
        //只更新name属性
        fun clickName() {
            val toFloat = Random().nextInt(100).toFloat()
            goods.name = "小明:$toFloat"
            goods.price = toFloat

        }
        //会更新所有属性
        fun clickDetails() {
            val toFloat = Random().nextInt(100).toFloat()
            goods.details = "帅气:$toFloat"
            goods.price=toFloat
        }
    }
}
```
nameView的刷新没有同时刷新priceView，而detailsView刷新的同时也刷新了priceView

实现了 Observable 接口的类允许注册一个监听器，当可观察对象的属性更改时就会通知这个监听器  
propertyId 就用于标识特定的字段

```
//监听可观察对象的属性改变
        goods.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when(propertyId){
                    com.example.demo003_databing.BR.name->
                        Log.i("aaa","name")
                    com.example.demo003_databing.BR.details->
                        Log.i("aaa","details")
                    com.example.demo003_databing.BR._all->
                        Log.i("aaa","all")
                    else->
                        Log.i("aaa","未知字段")
                }
            }
        })
```
### 2.ObservableField
ObservableField 可以理解为官方对BaseObservable中字段的注解和刷新等操作的封装，官方原生提供了对基本数据类型的封装，例如ObservableBoolean、ObservableByte、ObservableChar、ObservableShort、ObservableInt、ObservableLong、ObservableFloat、ObservableDouble以及ObservableParcelable，也可通过ObservableField 泛型来申明其他类型  
#### ObservableField源码

```
public class ObservableField<T> extends BaseObservableField implements Serializable {
    static final long serialVersionUID = 1L;
    private T mValue;

    /**
     * Wraps the given object and creates an observable object
     *
     * @param value The value to be wrapped as an observable.
     */
    public ObservableField(T value) {
        mValue = value;
    }

    /**
     * Creates an empty observable object
     */
    public ObservableField() {
    }

    /**
     * Creates an ObservableField that depends on {@code dependencies}. Typically,
     * ObservableFields are passed as dependencies. When any dependency
     * notifies changes, this ObservableField also notifies a change.
     *
     * @param dependencies The Observables that this ObservableField depends on.
     */
    public ObservableField(Observable... dependencies) {
        super(dependencies);
    }

    /**
     * @return the stored value.
     */
    @Nullable
    public T get() {
        return mValue;
    }

    /**
     * Set the stored value.
     *
     * @param value The new value
     */
    public void set(T value) {
        if (value != mValue) {
            mValue = value;
            notifyChange();
        }
    }
}
```

```
/**
 * @author: Luuuzi
 * @date: 2020-12-02
 * @description:
 */
class ObservableGoods(name: String, details: String, price: Float) {
    var name2:ObservableField<String>
    var details2:ObservableField<String>
    var price2:ObservableFloat
    init {
        this.name2= ObservableField(name)
        this.details2=ObservableField(details)
        this.price2=ObservableFloat(price)
    }

}
```
```
abstract class BaseObservableField extends BaseObservable {
    public BaseObservableField() {
    }

    public BaseObservableField(Observable... dependencies) {
        if (dependencies != null && dependencies.length != 0) {
            DependencyCallback callback = new DependencyCallback();

            for (int i = 0; i < dependencies.length; i++) {
                dependencies[i].addOnPropertyChangedCallback(callback);
            }
        }
    }

    class DependencyCallback extends Observable.OnPropertyChangedCallback {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            notifyChange();
        }
    }
}
```

使用
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <import type="com.example.demo003_databing.model.Goods" />

        <import type="com.example.demo003_databing.Test2Activity.ClickHandler" />
<!--        ObservableField-->
        <import type="com.example.demo003_databing.model.ObservableGoods" />
        <import type="com.example.demo003_databing.Test2Activity.ClickHandler2" />

        <variable
            name="goods"
            type="Goods" />

        <variable
            name="click"
            type="ClickHandler" />

        <variable
            name="observableGoods"
            type="ObservableGoods" />

        <variable
            name="click2"
            type="ClickHandler2" />
    </data>

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.appcompat.widget.LinearLayoutCompat
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <androidx.appcompat.widget.AppCompatTextView
                android:id="@+id/tv_name"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="@{goods.name}" />

            <androidx.appcompat.widget.AppCompatTextView
                android:id="@+id/tv_details"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="@{goods.details}" />

            <androidx.appcompat.widget.AppCompatTextView
                android:id="@+id/tv_price"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="@{String.valueOf(goods.price)}" />

            <androidx.appcompat.widget.AppCompatButton
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:onClick="@{()->click.clickName()}"
                android:text="修改name" />

            <androidx.appcompat.widget.AppCompatButton
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:onClick="@{()->click.clickDetails()}"
                android:text="修改details属性(所有属性)" />

            <androidx.appcompat.widget.AppCompatTextView
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:gravity="center"
                android:text="======分割线==========" />
<!--ObservableField-->
            <androidx.appcompat.widget.AppCompatTextView
                android:id="@+id/tv_name2"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="@{observableGoods.name2}" />

            <androidx.appcompat.widget.AppCompatTextView
                android:id="@+id/tv_details2"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="@{observableGoods.details2}" />

            <androidx.appcompat.widget.AppCompatTextView
                android:id="@+id/tv_price2"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="@{String.valueOf(observableGoods.price2)}" />

            <androidx.appcompat.widget.AppCompatButton
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:onClick="@{()->click2.clickName()}"
                android:text="修改name" />

            <androidx.appcompat.widget.AppCompatButton
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:onClick="@{()->click2.clickDetails()}"
                android:text="修改details属性(所有属性)" />
        </androidx.appcompat.widget.LinearLayoutCompat>
    </ScrollView>
</layout>
```

```
class Test2Activity : AppCompatActivity() {
    lateinit var goods: Goods
    lateinit var observableGoods: ObservableGoods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest2Binding>(this, R.layout.activity_test2)
        goods = Goods()
        goods.name = "小红"
        goods.details = "漂亮"
        goods.price = 2000f
        dataBindingUtil.goods = goods
        dataBindingUtil.click = ClickHandler()
        //监听可观察对象的属性改变
        goods.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    com.example.demo003_databing.BR.name ->
                        Log.i("aaa", "name")
                    com.example.demo003_databing.BR.details ->
                        Log.i("aaa", "details")
                    com.example.demo003_databing.BR._all ->
                        Log.i("aaa", "all")
                    else ->
                        Log.i("aaa", "未知字段")
                }
            }
        })
        observableGoods = ObservableGoods("张三", "英俊", 32f)
        dataBindingUtil.observableGoods = observableGoods
        dataBindingUtil.click2 = ClickHandler2()

    }

    //点击事件class
    inner class ClickHandler {
        //只更新name属性
        fun clickName() {
            val toFloat = Random().nextInt(100).toFloat()
            goods.name = "小明:$toFloat"
            goods.price = toFloat

        }

        //会更新所有属性
        fun clickDetails() {
            val toFloat = Random().nextInt(100).toFloat()
            goods.details = "帅气:$toFloat"
            goods.price = toFloat
        }
    }

    //点击事件class2(ObservableField)
    inner class ClickHandler2 {
        //只更新name属性
        fun clickName() {
            Log.i("aaa", "clickName2")
            val toFloat = Random().nextInt(100).toFloat()
            observableGoods.name2.set("李四:$toFloat")
        }

        //会更新所有属性
        fun clickDetails() {
            Log.i("aaa", "clickDetails2")
            val toFloat = Random().nextInt(100).toFloat()
            observableGoods.details2.set("高大：$toFloat")
            observableGoods.price2.set(toFloat)
        }
    }
}
```
### 3.ObservableCollection
dataBinding也提供了包装类用于替代原生的 List 和 Map，分别是ObservableList 和 ObservableMap,当其包含的数据发生变化时，绑定的视图也会随之进行刷新

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        ...

        <!--        ObservableCollection-->
        <import type="androidx.databinding.ObservableArrayList" />

        <import type="androidx.databinding.ObservableMap" />

        <variable
            name="list"
            type="ObservableArrayList&lt;String&gt;" />

        <variable
            name="map"
            type="ObservableMap&lt;String,String&gt;" />
        <!--定义变量设置集合下标-->
        <variable
            name="index"
            type="int" />

        <variable
            name="key"
            type="String" />
    </data>

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.appcompat.widget.LinearLayoutCompat
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            ...

<!--==================================-->
            <androidx.appcompat.widget.AppCompatTextView
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:gravity="center"
                android:text="======分割线ObservableCollection==========" />
            <androidx.appcompat.widget.AppCompatTextView
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="@{list[index],default=测试list}"/>
            <androidx.appcompat.widget.AppCompatTextView
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="@{map[key],default=测试map}"/>
            <androidx.appcompat.widget.AppCompatButton
                android:id="@+id/btn_modify3"
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="ObservableCollection修改"/>

        </androidx.appcompat.widget.LinearLayoutCompat>
    </ScrollView>
</layout>
```
activity代码

```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest2Binding>(this, R.layout.activity_test2)
        ...

//ObservableCollection使用
        val list = ObservableArrayList<String>()
        list.add("测试数据")
        dataBindingUtil.list = list

        val map = ObservableArrayMap<String,String>()
        map["name"] = "默认值"
        dataBindingUtil.map = map
        //设置对应的下标和key
        dataBindingUtil.index = 0
        dataBindingUtil.key = "name"

        dataBindingUtil.btnModify3.setOnClickListener {
            val toFloat = Random().nextInt(100).toFloat()
            list[0] = "小张的年龄$toFloat 岁"
            map["name"] = "小宇：$toFloat"
        }
    }
```
## 5、双向数据绑定
双向绑定的意思即为当数据改变时刷新视图，而视图改变时也可以改变数据

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <import type="com.example.demo003_databing.model.ObservableGoods"/>
        <variable
            name="goods"
            type="ObservableGoods" />
    </data>

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <TextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{goods.name2}"
            android:background="#ccc"/>
        <androidx.appcompat.widget.AppCompatEditText
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@={goods.name2}"/>
    </androidx.appcompat.widget.LinearLayoutCompat>
</layout>
```
activity代码
```
/**
 * @author: Luuuzi
 * @date: 2020-12-08
 * @description:双向数据绑定
 */
class Test3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest3Binding>(this, R.layout.activity_test3)
            dataBindingUtil.goods= ObservableGoods("小红","漂亮",18f)

    }
}
```
修改edit的内容，textview的内容也相应的修改
## 6、事件绑定
严格意义上来说，事件绑定也是一种变量绑定，只不过设置的变量是回调接口而已
事件绑定可用于以下多种回调事件

```
android:onClick //点击事件
android:onLongClick //长按事件
android:afterTextChanged //数据改变之后
android:onTextChanged //
```
xml设置事件对象和数据
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <import type="com.example.demo003_databing.model.User"/>
        <import type="com.example.demo003_databing.Test4Activity.UserPresenterClick"/>
        <variable
            name="users"
            type="User" />
        <variable
            name="userClick"
            type="UserPresenterClick" />
    </data>

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{users.name}"
            android:onClick="@{()->userClick.onUserNameClick(users)}"
            android:background="#f00"/>
        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{users.password}"
            android:background="#0f0"/>
        <EditText
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:afterTextChanged="@{userClick.ofterTextChange}"
            android:hint="用戶名" />
<!--        -->
        <androidx.appcompat.widget.AppCompatEditText
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:afterTextChanged="@{userClick.ofterUserpasswordChange}"
            android:hint="密码"/>

    </androidx.appcompat.widget.LinearLayoutCompat>
</layout>
```
在 Activity 内部新建一个 UserPresenterClick 类来声明 onClick() 和 afterTextChanged() 事件相应的回调方法
```
/**
 * @author: Luuuzi
 * @date: 2020-12-08
 * @description:事件绑定
 */
class Test4Activity : AppCompatActivity() {
    lateinit var user: User
    lateinit var dataBindingUtil: ActivityTest4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest4Binding>(this, R.layout.activity_test4)
        user = User("小明", "123456")
        dataBindingUtil.users=user
        dataBindingUtil.userClick=UserPresenterClick()

    }

    //点击事件
    inner class UserPresenterClick {
        fun onUserNameClick(user: User) {
            Toast.makeText(
                this@Test4Activity,
                "name:${user.name},password:${user.password}",
                Toast.LENGTH_SHORT
            ).show()
        }

        fun ofterTextChange(s: Editable) {
            Log.i("aaa","s:$s")
            user.name=s.toString()
            dataBindingUtil.users=user

        }

        fun ofterUserpasswordChange(s: Editable) {
            user.password=s.toString()
            dataBindingUtil.users=user
        }

    }
}
```
方法引用的方式与调用函数的方式类似，既可以选择保持事件回调方法的签名一致：@{userPresenter.afterTextChanged}，此时方法名可以不一样，但方法参数和返回值必须和原始的回调函数保持一致。也可以引用不遵循默认签名的函数：@{()->userPresenter.onUserNameClick(userInfo)}，这里用到了 Lambda 表达式，这样就可以不遵循默认的方法签名，将userInfo对象直接传回点击方法中。此外，也可以使用方法引用 :: 的形式来进行事件绑定
## 7、使用类方法
首先定义一个静态方法

```
/**
 * @author: Luuuzi
 * @date: 2020-12-09
 * @description:databing调用静态方法
 */
class StringUtils {
    companion object {
        @JvmStatic
        fun UpperCase(str: String): String {
            return str.toUpperCase()
        }
    }
}
```
xml
```
<!--1、导入类-->
<import type="com.example.demo003_databing.util.StringUtils"/>

<!--2、使用类方法-->
<androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{StringUtils.UpperCase(users.name)}"
            android:onClick="@{()->userClick.onUserNameClick(users)}"/>
```
## 8、运算符
### 1、基础运算符
DataBinding 支持在布局文件中使用以下运算符、表达式和关键字
- 算术 + - / * %
- 字符串合并 +
- 逻辑 && ||
- 二元 & | ^
- 一元 + - ! ~
- 移位 >> >>> <<
- 比较 == > < >= <=
- Instanceof
- Grouping ()
- character, String, numeric, null
- Cast
- 方法调用
- Field 访问
- Array 访问 []
- 三元 ?:

目前不支持以下操作
- this
- super
- new
= 显示泛型调用

此外，DataBinding 还支持以下几种形式的调用

#### 2、Null Coalescing
空合并运算符 ?? 会取第一个不为 null 的值作为返回值

```
 <TextView
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:text="@{user.name ?? user.password}" />
```
等价于

```
    android:text="@{user.name != null ? user.name : user.password}"
```
#### 3、属性控制
可以通过变量值来控制 View 的属性

```
 <TextView
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:text="可见性变化"
     android:visibility="@{user.male  ? View.VISIBLE : View.GONE}" />
```
#### 4、避免空指针异常
DataBinding 也会自动帮助我们避免空指针异常
例如，如果 "@{userInfo.password}" 中 userInfo 为 null 的话，userInfo.password 会被赋值为默认值 null，而不会抛出空指针异常
## 9、include 和 viewStub
### 1、include
对于 include 的布局文件，一样是支持通过 dataBinding 来进行数据绑定，此时一样需要在待 include 的布局中依然使用 layout 标签，声明需要使用到的变量  

layout_view.xml
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <import type="com.example.demo003_databing.model.User"/>
        <import type="com.example.demo003_databing.model.Goods"/>
        <variable
            name="userInfo"
            type="User" />
        <variable
            name="good"
            type="Goods" />
    </data>

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#ccc"
        android:orientation="vertical">
        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="我的include布局"/>
        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{userInfo.password}"/>
        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{good.details}"/>
    </androidx.appcompat.widget.LinearLayoutCompat>
</layout>
```
activity_test5.xml
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:bind="http://schemas.android.com/apk/res-auto">

    <data>

        <import type="com.example.demo003_databing.model.User" />

        <import type="com.example.demo003_databing.model.Goods" />

        <variable
            name="userInfo"
            type="User" />

        <variable
            name="good"
            type="Goods" />
    </data>

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="测试include" />
<!--只有绑定了变量，include布局才能使用到对应的参数-->
        <include
            layout="@layout/layout_view"
            bind:userInfo="@{userInfo}"
            bind:good="@{good}"/>
    </androidx.appcompat.widget.LinearLayoutCompat>
</layout>
```
Test5Activity
```
/**
 * @author: Luuuzi
 * @date: 2020-12-09
 * @description:
 */
class Test5Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //只关联了activity_test5.xml没关联layout_view.xml
        val dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest5Binding>(this, R.layout.activity_test5)
        dataBindingUtil.userInfo = User("好的", "123456")
        val goods = Goods()
        goods.details="帅气"
        dataBindingUtil.good=goods
    }
}
```
### 2、viewStub
dataBinding 一样支持 ViewStub 布局  

layout_stub.xml
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <import type="com.example.demo003_databing.model.User"/>
        <import type="com.example.demo003_databing.model.Goods"/>
        <variable
            name="userInfo"
            type="User" />
        <variable
            name="good"
            type="Goods" />
    </data>

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#ccc"
        android:orientation="vertical">
        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="我的stub布局"/>
        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{userInfo.password}"/>
        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="@{good.details}"/>
    </androidx.appcompat.widget.LinearLayoutCompat>
</layout>
```
activity_test5.xml
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <import type="com.example.demo003_databing.model.User" />

        <import type="com.example.demo003_databing.model.Goods" />

        <variable
            name="userInfo"
            type="User" />

        <variable
            name="good"
            type="Goods" />
    </data>

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/tv1"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:text="viewstub可见性" />
        <ViewStub
            android:id="@+id/view_stub"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout="@layout/layout_stub" />
    </androidx.appcompat.widget.LinearLayoutCompat>
</layout>
```

```
class Test5Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest5Binding>(this, R.layout.activity_test5)

        //获取到viewstub对象(只有调用了inflateviewstub才会显示
        val inflate = dataBindingUtil.viewStub.viewStub?.inflate()
        //设置可见性
        dataBindingUtil.tv1.setOnClickListener {
            if (inflate?.visibility == View.VISIBLE) {
                inflate.visibility = View.GONE
            } else {
                inflate?.visibility = View.VISIBLE
            }
        }
    }
}
```
如果需要为 ViewStub 绑定变量值，则 ViewStub 文件一样要使用 layout 标签进行布局，主布局文件使用自定义的 bind 命名空间将变量传递给 ViewStub
```
    <ViewStub
        android:id="@+id/view_stub"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout="@layout/view_stub"
        bind:userInfo="@{userInfo}" />
```
如果在 xml 中没有使用 bind:userInfo="@{userInf}"对 ViewStub 进行数据绑定，则可以等到当 ViewStub Inflate 时再绑定变量，此时需要为 ViewStub 设置 setOnInflateListener回调函数，在回调函数中进行数据绑定
```
dataBindingUtil.viewStub.setOnInflateListener(object : ViewStub.OnInflateListener {
            override fun onInflate(stub: ViewStub?, inflated: View?) {
                //如果在 xml 中没有使用 bind:userInfo="@{userInf}" 对 viewStub 进行数据绑定
                //那么可以在此处进行手动绑定
                    val bind = DataBindingUtil.bind<LayoutViewBinding>(inflated!!)
                    bind?.userInfo = User("小米", "123456")
            }
        })
```
## 10.BindingAdapter
BindingAdapter 这个注解用于支持自定义属性，或者是修改原有属性。注解值可以是已有的 xml 属性，例如android:src、android:text等，也可以自定义属性然后在xml中使用  

**举例**：对于一个ImageView，我们希望在某个变量值发生变化时，可以动态改变显示的图片，此时就可以通过 BindingAdapter 来实现

### 自定义属性
xml
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:bind="http://schemas.android.com/apk/res-auto">

    <data>

        <import type="com.example.demo003_databing.util.ImageUtils" />

        <import type="com.example.demo003_databing.Test7Activity.HandlerOnclick" />

        <variable
            name="image"
            type="ImageUtils" />

        <variable
            name="hd"
            type="HandlerOnclick" />
    </data>

    <androidx.appcompat.widget.LinearLayoutCompat
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <!--绑定属性：aaa bind:url和@BindingAdapter("aaa")的值对应-->
        <androidx.appcompat.widget.AppCompatImageView
            android:id="@+id/imageview"
            android:layout_width="match_parent"
            android:layout_height="180dp"
            android:src="@drawable/ic_launcher_background"
            bind:aaa="@{image.url}" />

        <androidx.appcompat.widget.AppCompatButton
            android:id="@+id/button"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:layout_marginTop="3dp"
            android:onClick="@{()->hd.onClick(image)}"
            android:text="改变uri" />
    </androidx.appcompat.widget.LinearLayoutCompat>
</layout>
```
java
```
/**
 * @author: Luuuzi
 * @date: 2020-12-15
 * @description:
 */
class Test7Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest7Binding>(this, R.layout.activity_test7)
        dataBindingUtil.image = ImageUtils("测试")
        dataBindingUtil.hd = HandlerOnclick()
    }

    //    放在此处则报错
//    @BindingAdapter("aaa")
//    fun loadImage(imageView: ImageView, cc: String) {
//        Log.i("aaa", "rul:$cc")
//    }

    class HandlerOnclick {

        fun onClick(imageUtils: ImageUtils): Boolean {
            imageUtils.url.set("xxxxx${Random.nextInt(1000)}")
            return true
        }
    }
}
/**
 * 自定义属性 aaa
 *通过BindingAdapter绑定aaa属性，当aaa发生变化时，调用此方法(可以设置加载图片)
 * BindingAdapter与对应的方法参数个数和类型有关，与方法名和参数名无关(view类型以及bind参数类型)
 */
@BindingAdapter("aaa")
fun loadImage(imageView: ImageView, cc: String) {
    //当aaa属性的值变化时，此方法会执行
    Log.i("aaa", "rul:$cc")
}
```

### 修改原生属性
以下方法会将布局文件中所有以android:text='@{String}'方式引用到的String类型变量加上后缀-Button
```
/**
 * 修改原生属性,但是对java代码设置无效
 */
@BindingAdapter("android:text")
fun setText(view: AppCompatButton, text: String) {
    Log.i("aaa","原生属性：$text")
    view.text = "$text-Button"
}
```


```
<!--覆盖原生属性：android:text，对应的属性值必须为'@{"XXX"}'这种格式才有效-->
        <androidx.appcompat.widget.AppCompatButton
            android:id="@+id/button"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:layout_marginTop="3dp"
            android:onClick="@{()->hd.onClick(image)}"
            android:text='@{"改变uri"}' />
```
==注意，通过java代码设置的无效==

```
dataBindingUtil.button.text = "修改内容${Random.nextInt(1000)}"
```
## 11、BindingConversion
- 对数据进行转换，或者进行类型转换    
- 优先级比BindingAdapter要高  

### 转换数据
==和BindingAdapter类似，以下方法会将布局文件中所有以@{String}方式引用到的String类型变量加上后缀-conversionString==

java
```
/**
 * 数据转换
 */
@BindingConversion
fun conversionString(text: String):String{
    return "$text --conversation"
}
```
xml
```
        <!--覆盖原生属性：android:text，对应的属性值必须为'@{"XXX"}'这种格式才有效-->
        <androidx.appcompat.widget.AppCompatButton
            android:id="@+id/button"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:layout_marginTop="3dp"
            android:onClick="@{()->hd.onClick(image)}"
            android:text='@{"改变uri"}' />
<!--        BindingConversion -->
        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:layout_marginTop="3dp"
            android:text='@{"XXX"}'/>
```
Button 来说，BindingAdapter和BindingConversion同时生效了，且BindingConversion 的优先级要高些

### 转换数据类型
kotlin 
```
/**
 * 类型转换 将string转换成drawable
 */
@BindingConversion
fun conversionType(text: String): Drawable {
    if (text == "红色") {
        return ColorDrawable(Color.RED)
    } else if (text == "蓝色") {
        return ColorDrawable(Color.BLUE)
    } else {
        return ColorDrawable(Color.TRANSPARENT)
    }
}

/**
 * 类型转换2 将string转换成int
 */
@BindingConversion
fun conversionType2(text: String): Int {
    if (text == "红色") {
        return Color.RED
    } else if (text == "蓝色") {
        return Color.BLUE
    } else {
        return Color.TRANSPARENT
    }
}
```

xml
```
<!--        类型转换
        background使用：conversionType
        textColor使用：conversionType-->
        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background='@{"红色"}'
            android:padding="20dp"
            android:text="红色背景蓝色字"
            android:textColor='@{"蓝色"}' />

        <androidx.appcompat.widget.AppCompatTextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            android:background='@{"蓝色"}'
            android:padding="20dp"
            android:text="蓝色背景红色字"
            android:textColor='@{"红色"}' />
```
## 12、Array、List、Set、Map
- dataBinding支持在布局文件中使用数组、Lsit、Set和Map，且在布局文件中都可以通过 list[index] 的形式来获取元素  
- 为了和 variable 标签的尖括号区分开，在声明Lsit<String>之类的数据类型时，需要使用尖括号的转义字符


```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <import type="java.util.List" />
        <import type="java.util.Map" />
        <import type="java.util.Set" />
        <import type="android.util.SparseArray" />
        <variable
            name="array"
            type="String[]" />
        <variable
            name="list"
            type="List&lt;String&gt;" />
        <variable
            name="map"
            type="Map&lt;String, String&gt;" />
        <variable
            name="set"
            type="Set&lt;String&gt;" />
        <variable
            name="sparse"
            type="SparseArray&lt;String&gt;" />
        <variable
            name="index"
            type="int" />
        <variable
            name="key"
            type="String" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context=".Main7Activity">

        <TextView
            ···
            android:text="@{array[1]}" />
        <TextView
            ···
            android:text="@{sparse[index]}" />
        <TextView
            ···
            android:text="@{list[index]}" />
        <TextView
            ···
            android:text="@{map[key]}" />
        <TextView
            ···
            android:text='@{map["leavesC"]}' />
        <TextView
            ···
            android:text='@{set.contains("xxx")?"xxx":key}' />
    </LinearLayout>
</layout>
```

## 13、资源引用
dataBinding 支持对尺寸和字符串这类资源的访问  
dimens.xml
```
    <dimen name="paddingBig">190dp</dimen>
    <dimen name="paddingSmall">150dp</dimen>
```
strings.xml
```
    <string name="format">%s is %s</string>
```
xml
```
    <data>
        <variable
            name="flag"
            type="boolean" />
    </data>       
    <Button
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:paddingLeft="@{flag ? @dimen/paddingBig:@dimen/paddingSmall}"
         android:text='@{@string/format("leavesC", "Ye")}'
         android:textAllCaps="false" />
```
## 14、其他
待补充
