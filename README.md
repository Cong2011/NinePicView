# NinePicView[![](https://jitpack.io/v/Cong2011/NinePicView.svg)](https://jitpack.io/#Cong2011/NinePicView)
九宫格图片控件显示、编辑、拖拽排序

## 添加依赖：
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Cong2011:NinePicView:版本号'
	}

## 三种配置方式：
（下层配置会覆盖上层配置）（必须配置ImageLoader）

1、application或静态构造代码块中，调用

NinePicView.init()

2、layout.xml中使用app:xxx属性配置

3、使用NinePicView的set方法配置

说明：

当前版本支持Androidx（RecyclerView:1.1.0、appCompat:1.2.0）（support:28.0.0请使用1.0.2） 

不支持9图以上

基于：https://github.com/panyiho/NineGridView （原项目使用此资源，但该资源不支持拖拽效果）