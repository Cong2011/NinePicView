# NinePicView
九宫格图片控件显示、编辑、拖拽排序

## 添加依赖：（可能暂未配置，先提前占个位；如果无法使用请下载源码后添加module依赖）
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
	        implementation 'com.github.Cong2011:NinePicView:1.0.0'
	}

## 三种配置方式：（下层配置会覆盖上层配置）（必须配置ImageLoader）

1、application或静态构造代码块中，调用

NinePicView.init()

2、layout.xml中使用app:xxx属性配置

3、使用NinePicView的set方法配置

说明：

暂不支持9图以上；暂不支持AndroidX；需额外添加 implementation 'com.android.support:design:28.0.0'依赖(因使用RecyclerView)

基于：https://github.com/panyiho/NineGridView （原项目使用此资源）
后因项目要求编辑时可拖动图片排序，但此资源不含拖拽功能。
于是修改为RecyclerView+ItemTouchHelper核心，增加拖拽效果，并修改一些细节如测量方法等。
