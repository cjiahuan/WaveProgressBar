# WaveProgressbar
wave progress bar

### ScreenShots
<img src="https://github.com/cjhandroid/WaveProgressBar/blob/master/ezgif.com-video-to-gif%20(4).gif" />

### v1.0.0

实现基本功能

### How to use

#### gradle

```
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

  ```
  dependencies {
	        compile 'com.github.cjhandroid:WaveProgressBar:v1.0.0'
	}
  ```

### Create by xml

事实上，用代码动态创建也是可以的。

```
<cjh.WaveProgressBarlibrary.WaveProgressBar
        android:id="@+id/waveProgressbar"
        android:layout_width="160dp"
        android:layout_height="160dp"
        app:arc_color="@android:color/white"
        app:cavans_bg="#3C3C3C"
        app:progress="30"
        app:shape="circle"
        app:text_size="20sp" />
```

### attrs

|参数名|参数含义|
|--- |:--- |
|width |宽 |
|height |高 |
|text_follow_progress | 是否由进度来控制文字|
|dwave | 振幅|
|wave_duration | 振幅的快慢|
|max | 最大进度|
|progress | 当前进度|
|behind_wave_color | 底部弧线的颜色|
|front_wave_color | 顶部弧线的颜色|
|arc_color | 矩形四角凹圆弧的颜色|
|shape | 形状 circle/square|
|cavans_bg | 背景色|
|text_color | 文字颜色|
|text_margin_top| 文字距离顶部的高度|
|text_size | 文字大小|
|auto_text_size | 文字大小是否自动调整 默认 true|

### The most important

```
 app:arc_color="@android:color/white"
```

这个属性是设置四角的凹圆弧的颜色，默认是白色，这个属性基本属于必须要设置的，如果不设置，就无法达到你想要的圆形的效果。