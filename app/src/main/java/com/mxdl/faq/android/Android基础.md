### 1.四大组件是什么
* (1)Activity是整个应用程序的门面，主要负责应用程序当中数据的展示，是各种各样控件的容器，是用户和应用程序之间交互的接口（美女）
* (2)Service在前台不可见，但是承担大部分数据处理工作，它和Activity的地位是并列的，区别在于Activity运行于前台，Service运行于后台，没有图形用户界面，通常他为其他的组件提供后台服务或监控其他组件的运行状态,适合执行不需要和用户交互且需要长期运行的任务（劳模）
* (3)ContentProvider：为不同的应用程序之间数据访问提供统一的访问接口，通常它与ContentResolver结合使用，一个是应用程序使用ContentProvider来暴露自己的数据，而另外一个是应用程序通过ContentResolver来访问数据（国家档案馆）
* (4)Broadcast Receiver: 实现消息的异步接收，他非常类似事件编程中的监听器，但他与普通事件监听器有所不同，普通的事件监听器监听的事件源是程序中的控件，而BroadcastReceiver监听的事件源是Android应用中其他的组件（国家监察局）
>内容提供者，使一个应用程序的指定数据集它提供了一种跨进程数据共享的方式，当数据被修改后，ContentResolver接口的notifyChange函数通知那些注册监控特定URI的ContentObserver对象。
如果ContentProvider和调用者在同一进程中，ContentProvider的方法(query/insert/update/delete等)和调用者在同一线程中；如果ContentProvider和调用者不在同一进程，ContentProvider方法会运行在它自身进程的一个Binder线程中。
### 2.四大组件的生命周期和简单用法
##### (1) Activity
onCreate()->onStart()->onResume()->onPause()->onStop()->onDestory()
* onCreate()：为Activity设置布局，此时界面还不可见
* onStart(): Activity可见但还不能与用户交互，不能获得焦点
* onRestart(): 重新启动Activity时被回调
* onResume(): Activity可见且可与用户进行交互
* onPause(): 当前Activity暂停，不可与用户交互，但还可见。在新Activity启动前被系统调用保存现有的Activity中的持久数据、停止动画等。
* onStop(): 当Activity被新的Activity覆盖不可见时被系统调用
* onDestory(): 当Activity被系统销毁杀掉或是由于内存不足时调用
##### (2) Service
* onBind方式绑定的：onCreate->onBind->onUnBind->onDestory（不管调用bindService几次，onCreate只会调用一次，onStart不会被调用，建立连接后，service会一直运行，直到调用unBindService或是之前调用的bindService的Context不存在了，系统会自动停止Service,对应的onDestory会被调用）
* startService启动的：onCreate->onStartCommand->onDestory(start多次，onCreate只会被调用一次，onStart会调用多次，该service会在后台运行，直至被调用stopService或是stopSelf)
* 又被启动又被绑定的服务，不管如何调用onCreate()只被调用一次，startService调用多少次，onStart就会被调用多少次，而unbindService不会停止服务，必须调用stopService或是stopSelf来停止服务。必须unbindService和stopService(stopSelf）同时都调用了才会停止服务。
##### (3) BroadcastReceiver
* 动态注册：存活周期是在Context.registerReceiver和Context.unregisterReceiver之间，BroadcastReceiver每次收到广播都是使用注册传入的对象处理的。
* 静态注册：进程在的情况下，receiver会正常收到广播，调用onReceive方法；生命周期只存活在onReceive函数中，此方法结束，BroadcastReceiver就销毁了。onReceive()只有十几秒存活时间，在onReceive()内操作超过10S，就会报ANR。
进程不存在的情况，广播相应的进程会被拉活，Application.onCreate会被调用，再调用onReceive。
##### (4) ContentProvider
应该和应用的生命周期一样，它属于系统应用，应用启动时，它会跟着初始化，应用关闭或被杀，它会跟着结束。
### 3.Activity之间的通信方式
* (1)通过Intent方式传递参数跳转
* (2)通过广播方式
* (3)通过接口回调方式
* (4)借助类的静态变量或全局变量
* (5)借助SharedPreference或是外部存储，如数据库或本地文件

### 4.Activity各种情况下的生命周期
* 两个Activity(A->B)切换(B正常的Activity)的生命周期：onPause(A)->onCreate(B)->onStart(B)->onResume(B)->oStop(A)
* 这时如果按回退键回退到A  onPause(B)->onRestart(A)->onStart(A)->onResume(A)->oStop(B)->oDestory(B)
* 如果在切换到B后调用了A.finish()，则会走到onDestory(A)，这时点回退键会退出应用
* Activity(A)启动后点击Home键再回到应用的生命周期：onPause(A)->oStop(A)->onRestart(A)->onStart(A)->onResume(A)

### 5.横竖屏切换的时候，Activity 各种情况下的生命周期
* 切换横屏时：onSaveInstanceState->onPause->onStop->onDestory->onCreate->onStart->onRestoreInstanceState->onResume
* 切换竖屏时：会打印两次相同的log
onSaveInstanceState->onPause->onStop->onDestory->onCreate->onStart->onRestoreInstanceState->onResume->onSaveInstanceState->onPause->onStop->onDestory->onCreate->onStart->onRestoreInstanceState->onResume
* 如果在AndroidMainfest.xml中修改该Activity的属性，添加android:configChanges="orientation"
横竖屏切换，打印的log一样，同1)
* 如果AndroidMainfest.xml中该Activity中的android:configChanges="orientation|keyboardHidden"，则只会打印
onConfigurationChanged->

### 6.Activity与Fragment之间生命周期比较
* Fragment生命周期：onAttach->onCreate->onCreateView->onActivityCreated->onStart->onResume->onPause->onStop->onDestoryView->onDestory->onDetach
* 切换到该Fragment：onAttach->onCreate->onCreateView->onActivityCreated->onStart->onResume
* 按下Power键：onPause->onSaveInstanceState->onStop
* 点亮屏幕解锁：onStart->onRestoreInstanceState->onResume
* 切换到其他Fragment: onPause->onStop->onDestoryView
* 切回到该Fragment: onCreateView->onActivityCreated->onStart->onResume
* 退出应用：onPause->onStop->onDestoryView->onDestory->onDetach

### 7.Activity上有Dialog的时候按Home键时的生命周期
AlertDialog并不会影响Activity的生命周期，按Home键后才会使Activity走onPause->onStop，AlertDialog只是一个组件，并不会使Activity进入后台。

### 8.两个Activity 之间跳转时必然会执行的是哪几个方法？
前一个Activity的onPause，后一个Activity的onResume

### 9.前台切换到后台，然后再回到前台，Activity生命周期回调方法。弹出Dialog，生命值周期回调方法。
* (1)前台切换到后台，会执行onPause->onStop，再回到前台，会执行onRestart->onStart->onResume
* (2)弹出Dialog，并不会影响Activity生命周期
### 10.Activity的四种启动模式对比
* (1)standard：标准启动模式（默认），每启动一次Activity，都会创建一个实例，即使从ActivityA startActivity ActivityA,也会再次创建A的实例放于栈顶，当回退时，回到上一个ActivityA的实例。
* (2)singleTop：栈顶复用模式，每次启动Activity，如果待启动的Activity位于栈顶，则不会重新创建Activity的实例，即不会走onCreate->onStart，会直接进入Activity的onPause->onNewIntent->onResume方法
* (3)singleInstance: 单一实例模式，整个手机操作系统里只有一个该Activity实例存在，没有其他Actvity,后续请求均不会创建新的Activity。若task中存在实例，执行实例的onNewIntent()。应用场景：闹钟、浏览器、电话
* (4)singleTask：栈内复用，启动的Activity如果在指定的taskAffinity的task栈中存在相应的实例，则会把它上面的Activity都出栈，直到当前Activity实例位于栈顶，执行相应的onNewIntent()方法。如果指定的task不存在，创建指定的taskAffinity的task,taskAffinity的作用，进入指写taskAffinity的task,如果指定的task存在，将task移到前台，如果指定的task不存在，创建指定的taskAffinity的task. 应用场景：应用的主页面
### 11.Activity状态保存于恢复
* Activity被主动回收时，如按下Back键，系统不会保存它的状态，只有被动回收时，虽然这个Activity实例已被销毁，但系统在新建一个Activity实例时，会带上先前被回收Activity的信息。在当前Activity被销毁前调用onSaveInstanceState(onPause和onStop之间保存)，重新创建Activity后会在onCreate后调用onRestoreInstanceState（onStart和onResume之间被调用），它们的参数Bundle用来数据保存和读取的。
* 保存View状态有两个前提：View的子类必须实现了onSaveInstanceState; 必须要设定Id，这个ID作为Bundle的Key

### 12.fragment各种情况下的生命周期
正常情况下的生命周期：onAttach->onCreate->onCreateView->onActivityCreated->onStart->onResume->onPause->onStop->onDestoryView->onDestory->onDetach
* Fragment在Activity中replace  onPause(旧)->onAttach->onCreate->onCreateView->onActivityCreated->onStart->onResume->onStop(旧)->onDestoryView(旧)
如果添加到backStack中，调用remove()方法fragment的方法会走到onDestoryView，但不会执行onDetach()，即fragment本身的实例是存在的，成员变量也存在，但是view被销毁了。如果新替换的Fragment已在BackStack中，则不会执行onAttach->onCreate
### 13.Fragment状态保存onSaveInstanceState是哪个类的方法，在什么情况下使用？
在对应的FragmentActivity.onSaveInstanceState方法会调用FragmentController.saveAllState，其中会对mActive中各个Fragment的实例状态和View状态分别进行保存.当Activity在做状态保存和恢复的时候, 在它其中的fragment自然也需要做状态保存和恢复.

### 14.Fragment.startActivityForResult是和FragmentActivity的startActivityForResult？
如果希望在Fragment的onActivityResult接收数据，就要调用Fragment.startActivityForResult， 而不是Fragment.getActivity().startActivityForResult。Fragment.startActivityForResult->FragmentActivitymHost.HostCallbacks.onStartActivityFromFragment->FragmentActivity.startActivityFromFragment。如果request=-1则直接调用FragmentActivity.startActivityForResult，它会重新计算requestCode，使其大于0xfffff。
### 15.如何实现Fragment的滑动？
ViewPager+FragmentPagerAdapter+List<Fragment>
### 16.fragment之间传递数据的方式？
* (1) 在相应的fragment中编写方法，在需要回调的fragment里获取对应的Fragment实例，调用相应的方法；
* (2) 采用接口回调的方式进行数据传递；
a)在Fragment1中创建一个接口及接口对应的set方法; 
b) 在Fragment1中调用接口的方法；
c)在Fragment2中实现该接口；
* (3) 利用第三方开源框架EventBus
### 17.service和activity怎么进行数据交互？
* (1) 通过bindService启动服务，可以在ServiceConnection的onServiceConnected中获取到Service的实例，这样就可以调用service的方法，如果service想调用activity的方法，可以在service中定义接口类及相应的set方法，在activity中实现相应的接口，这样service就可以回调接口言法；
* (2) 通过广播方式
* (3) 消息信使（Messeager）
### 18.说说ContentProvider、ContentResolver、ContentObserver 之间的关系
* ContentProvider实现各个应用程序间数据共享，用来提供内容给别的应用操作。如联系人应用中就使用了ContentProvider，可以在自己应用中读取和修改联系人信息，不过需要获取相应的权限。它也只是一个中间件，真正的数据源是文件或SQLite等。
* ContentResolver内容解析者，用于获取内容提供者提供的数据，通过ContentResolver.notifyChange(uri)发出消息
* ContentObserver内容监听者，可以监听数据的改变状态，观察特定Uri引起的数据库变化，继而做一些相应的处理，类似于数据库中的触发器，当ContentObserver所观察的Uri发生变化时，便会触发它。
### 19.请描述一下广播BroadcastReceiver的理解
BroadcastReceiver是一种全局监听器，用来实现系统中不同组件之间的通信。有时候也会用来作为传输少量而且发送频率低的数据，但是如果数据的发送频率比较高或者数量比较大就不建议用广播接收者来接收了，因为这样的效率很不好，因为BroadcastReceiver接收数据的开销还是比较大的。
### 20.广播的分类
* (1)普通广播：完全异步的，可以在同一时刻（逻辑上）被所有接收者接收到，消息传递的效率比较高，并且无法中断广播的传播。
* (2)有序广播：发送有序广播后，广播接收者将按预先声明的优先级依次接收Broadcast。优先级高的优先接收到广播，而在其onReceiver()执行过程中，广播不会传播到下一个接收者，此时当前的广播接收者可以abortBroadcast()来终止广播继续向下传播，也可以将intent中的数据进行修改设置，然后将其传播到下一个广播接收者。 sendOrderedBroadcast(intent, null);//发送有序广播
* (3)粘性广播：sendStickyBroadcast()来发送该类型的广播信息，这种的广播的最大特点是，当粘性广播发送后，最后的一个粘性广播会滞留在操作系统中。如果在粘性广播发送后的一段时间里，如果有新的符合广播的动态注册的广播接收者注册，将会收到这个广播消息，虽然这个广播是在广播接收者注册之前发送的，另外一点，对于静态注册的广播接收者来说，这个等同于普通广播。

### 21.广播使用的方式和场景
* (1)App全局监听：在AndroidManifest中静态注册的广播接收器，一般我们在收到该消息后，需要做一些相应的动作，而这些动作与当前App的组件，比如Activity或者Service的是否运行无关，比如我们在集成第三方Push SDK时，一般都会添加一个静态注册的BroadcastReceiver来监听Push消息，当有Push消息过来时，会在后台做一些网络请求或者发送通知等等。
* (2)组件局部监听：这种主要是在Activity或者Service中使用registerReceiver()动态注册的广播接收器，因为当我们收到一些特定的消息，比如网络连接发生变化时，我们可能需要在当前Activity页面给用户一些UI上的提示，或者将Service中的网络请求任务暂停。所以这种动态注册的广播接收器适合特定组件的特定消息处理。
### 22.在manifest 和代码中如何注册和使用BroadcastReceiver?
* (1)mainfest中注册:静态注册的广播接收者就是一个常驻在系统中的全局监听器，也就是说如果你应用中配置了一个静态的BroadcastReceiver，而且你安装了应用而无论应用是否处于运行状态，广播接收者都是已经常驻在系统中了。
```
<receiver android:name=".MyBroadcastReceiver">
    <intent-filter>
        <action android:name="com.smilexie.test.intent.mybroadcastreceiver"/>
    </intent-filter>
</receiver>
```
* (2)动态注册:动态注册的广播接收者只有执行了registerReceiver(receiver, filter)才会开始监听广播消息，并对广播消息作为相应的处理。
```
IntentFilter fiter = new IntentFilter("com.smilexie.test.intent.mybroadcastreceiver");
MyBroadcastReceiver receiver = new MyBroadcastReceiver();
registerReceiver(receiver, filter);
```
//撤销广播接受者的动态注册
```
unregisterReceiver(receiver);
```
### 23.本地广播和全局广播有什么差别？
* (1)LocalBroadcastReceiver仅在自己的应用内发送接收广播，也就是只有自己的应用能收到，数据更加安全。广播只在这个程序里，而且效率更高。只能动态注册，在发送和注册的时候采用LocalBroadcastManager的sendBroadcast方法和registerReceiver方法。
* (2)全局广播：发送的广播事件可被其他应用程序获取，也能响应其他应用程序发送的广播事件（可以通过 exported–是否监听其他应用程序发送的广播 在清单文件中控制） 全局广播既可以动态注册，也可以静态注册。

### 24.AlertDialog,popupWindow,Activity区别
* (1)Popupwindow在显示之前一定要设置宽高，Dialog无此限制。
* (2)Popupwindow默认不会响应物理键盘的back，除非显示设置了popup.setFocusable(true);而在点击back的时候，Dialog会消失。
* (3)Popupwindow不会给页面其他的部分添加蒙层，而Dialog会。
* (4)Popupwindow没有标题，Dialog默认有标题，可以通过dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);取消标题
* (5)二者显示的时候都要设置Gravity。如果不设置，Dialog默认是Gravity.CENTER。
* (6)二者都有默认的背景，都可以通过setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));去掉。
* (7)Popupwindow弹出后，取得了用户操作的响应处理权限，使得其他UI控件不被触发。而AlertDialog弹出后，点击背景，AlertDialog会消失。

### 25.Application和Activity的Context对象的区别
* (1)Application Context是伴随应用生命周期；不可以showDialog, startActivity, LayoutInflation
可以startService\BindService\sendBroadcast\registerBroadcast\load Resource values
* (2)Activity Context指生命周期只与当前Activity有关，而Activity Context这些操作都可以，即凡是跟UI相关的，都得用Activity做为Context来处理。
一个应用Context的数量=Activity数量+Service数量+1（Application数量）