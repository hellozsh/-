---
typora-root-url: ../../StudyNotes
---

[TOC]

# 基本语法

## 基础

###ABI

从Swift5.0开始ABI稳定了

####描述

ABI（Application Binary Interface）：应用程序二进制接口，描述了应用程序和操作系统之间，一个应用和它的库之间，或者应用的组成部分之间的低接口。ABI涵盖了各种细节，如：

+ 数据类型的大小、布局和对齐
+ 调用约定（控制着函数的参数如何传送以及如何接受返回值），例如，是所有的参数都通过栈传递，还是部分参数通过寄存器传递；哪个寄存器用于哪个函数参数；通过栈传递的第一个函数参数是最先push到栈上还是最后
+ 系统调用的编码和一个应用如何向操作系统进行系统调用
+ 以及在一个完整的操作系统ABI中，目标文件的二进制格式、程序库等等。

####ABI和API的区别

+ API是Application Programming Interface的缩写，即应用程序接口。 一个API是不同代码片段的连接纽带。它定义了一个函数的参数，函数的返回值，以及一些属性比如继承是否被允许。 因此**API是用来约束编译器的**：一个API是给编译器的一些指令，它规定了源代码可以做以及不可以做哪些事。
+ ABI是Application Binary Interface的缩写，应用程序二进制接口。 一个ABI是不同二进制片段的连接纽带。 它定义了函数被调用的规则：参数在调用者和被调用者之间如何传递，返回值怎么提供给调用者，库函数怎么被应用，以及程序怎么被加载到内存。 因此**ABI是用来约束链接器的**：一个ABI是无关的代码如何在一起工作的规则。 一个ABI也是不同进程如何在一个系统中共存的规则。 举例来说，在Linux系统中，一个ABI可能定义信号如何被执行，进程如何调用syscall，使用大端还是小端，以及栈如何增长

###编译流程

![编译流程](/Swift入门到精通笔记/编译流程.png)

oc和swift的编译器前端(clang和swiftc)都存放在Xcode内部(Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin)

1. 生成语法树: swiftc -dump-ast main.swift
2. 生成最简洁的SIL代码: swiftc -emit-sil main.swift
3. 生成LLVM IR代码: swiftc -emit-ir main.swift
4. 生成汇编代码: swiftc -emit-assembly main.swift 

###PlayGround

1. print("hello \(a)") // a是常量或变量
2. Command+Shift+Enter 运行整个playGround
3. Shift+Enter 只运行到鼠标位置处代码
4. import PlaygroundSupport    再PlaygroundPage.current.liveView = view （可以将view展示再playground上）
5. 显示markUp效果， Editor ---> Show Rendered Markup

```swift
//:# 一级标题
/*:
##基础语法
-变量
-常量
[苹果官网](www.apple.com)
[下一页](@next)
*/
```

## 数据类型

```swift
var num = 10
num+= 20
 
let age: Int
age = num // 常量不要求在编译时确定，只要使用之前赋值就行
// 常量、变量在初始化之前，都不能使用
//标识符(常量名、变量名、方法名)可以是任何字符,表情也可以
//Float， 32位，精度只有6位；Double，64位置，精度至少15位
```

### 常见数据类型

![常见数据类型](/Swift入门到精通笔记/常见数据类型.png)

### 字面量

字面量也较立即数

```swift
let character: Character = "🐶"
// 整数
let intDecimal = 17  // 十进制
let intBinary = 0b10001 // 二进制
let intOctal = 0O21 // 八进制
let intHextadecimal = 0x11 // 十六进制
//浮点数
let doubleDecimal = 125.0 // 十进制
let doubleHexadecimal1 = 0xFp2  // 十六进制,15*2^2,相当于十进制的60.0
let doubleHexadecimal2 = 0xFp-2  // 十六进制,15*2^-2，相当于十进制的3.75
// 整数和浮点数可以添加额外的零或者添加下划线来增强可读性
100_0000.000_000_1, 000123.456
//类型转换
let int1: UInt16 = 2_000
let int2: UInt8 = 1
let int3 = int1 + UInt16(int2)
// 元组
let error = (404, "Not Find")
error.0 // 404
error.1  // Not Find
let(code,message) = error
code // 404
let(code,_) = error // 忽略Not Find
let http200Status = (statusCode:200,description:"OK")
http200Status.statusCode
```

## 流程控制

### if-else

+ if后面的条件可以省略小括号、只能是Bool类型
+ 条件后面的大括号不可以省略的

### while

### repeat{}while条件

+ 相当于C语音中的do-while

### ~~++~~

不再支持；报错 Use of unresolved operator '++'; did you mean '+= 1'?

+ 对于var age = 10; ++age + ++age
+ 微软编译器结果:  12+12
+ Xcode编译器结果: 11+12

### for

+ 闭区间运算符: a...b, a<= 取值 <=b

  ```swift
  for i in 0...3 {
     print(names[i])  // 1 2 3 4
  }
  let rang = 1...3
  for i in rang {
    print(names[i]) // 2 3 4
  }
  // i默认是let,有需要时可以声明为var
  for var i in 1...3 {
    i += 5
    print(i)  // 6 7 8
  }
  for _ in 1...3 {
     print("123") // 123 123 123
  }
  // 区间运算符用在数组上
  for name in names[0...3] {
    print(name)  // 1 2 3 4
  }
  // 单侧区间:让区间朝一个方向尽可能的远
  for name in names[2...] { // 2、3、4
     print(name)  // 3 4 5
  }
  for name in names[...2] { // 0、1、2
     print(name) // 1 2 3
  }
  for name in names[..<2] { // 0、1
     print(name) // 1 2
  }
  
  let range = ...5 // 负无穷-5
  range.contains(-3) // true
  // 区间类型
  let range1: Range<Int> = 1..<3
  // 字符、字符串也能使用区间运算符、但默认不能用在for-in中
  let stringRange1 = "cc"..."ff"
  stringRange1.contains("cb")  // false
  stringRange1.contains("dz")  // true
  stringRange1.contains("fg")  // false
  // \0-～所有可能用到的ASCII字符
  let characterRange: ClosedRange<Character> = "\0"..."~"
  // 带间隔的区间值
  let hours = 11
  let hourInterval = 2
  // tickMark的取值:从4开始,累加2，不超过11
  for tickMark in stride(from:4,through:hours,by:hourInterval) {
     print(tickMark) // 4 6 8 10
  }
  
  // 将所有正数加起来
  var numbers = [10,20,-10,-20,30,-30]
  var sum = 0
  for num in numbers where num>0 {
    sum += num
  } // 60
  print(sum) // 60 ; where num>0相当于不符合条件continue
  ```

### switch

1. case、default后面不能写大括号{}
2. 默认可以不写break，并不会贯穿到后面的条件
3. 使用fallthrough可以实现贯穿效果
4. switch必须要保证能处理所有情况
5. case、default后面至少要有一条语句
6. 如果能保证已处理所有情况，也可以不必使用default
7. switch也支持Character、String类型
8. 如果不想做任何事，加个break既可

```swift
let names = [1,2,3,4,5]
var number = 1
switch number {
  case 1:
     print("number is 1")
     break
  case 2:
     print("number is 2")
     fallthrough  // 会贯穿到下一个判断
  default:
     print("number is 2")
} // number is 1

let string = "jack"
switch string {
  case "jack":
    fallthrough
  case "Rose":
    print("12")
  default:
    break
}   // 12

// 区间匹配
let count = 62
switch count {
  case 0:
    print("none")
  case 1..<5:
    print("a few")
  default:
    print("many")
} // many

// 元组匹配
var point = (1,1)
switch point {
  case (0,0):
    print("the origin")
  case (_,0):
    print("on the x-axis") // 左边什么值都可以
  case(-2...2,-2...2):
    print("inside the box")
  default:
    print("outside the box")
} // inside the box

// 值绑定
point = (2,0)
switch point {
  case (let x,0):
    print("on the x-axis with an x value of \(x)")
  case let(x,y):
    print("somewhere else at (\(x),\(y))")
} // on the x-axis with an x value of 2


// 标签语句
outer: for i in 1...4 {
  for k in 1...4 {
    if k==3 {
      continue outer
    }
    if i==3 {
      break outer
    }
    print("i==\(i),k==\(k)")
  }
} // i==1,k==1   i==1,k==2   i==2,k==1   i==2,k==2
```

#函数

1. 形参默认是let，也只能是let

2. 隐式返回，如果整个函数体是一个单一表达式,那么函数会隐式返回整个表达式

   ```swift
   func sum(v1: Int, v2: Int) -> Int {
     v1+v2 // 不用写return,隐式返回
   }
   // 返回元组，实现返回多返回值
   func calculate(v1: Int, v2:Int) -> (sum:Int,difference:Int,average:Int) {
     let sum = v1+v2;
     return (sum,v1-v2,sum>>1)
   }
   let result = calculate(v1:20,v2:10)
   result.sum
   result.difference
   result.average
   ```

3. 函数的文档注释(鼠标在方法上按住option既可出现)[更多语法规范](https://swift.org/documentation/api-design-guidelines/)

   ```swift
   /// 求和[概述]
   ///
   /// 将2个整数相加[更详细的描述]
   ///
   /// - Parameter v1: 第一个整数
   /// - Parameter v2: 第二个整数
   /// - Returns : 2个整数的和
   ///
   /// - Note:传入2个整数既可[批注]
   ///
   func sum(v1: Int, v2: Int) -> Int {
     v1+v2 
   }
   ```

4. 参数标签、默认参数值

   ```swift
   // 参数标签
   func goToWork(at time: String) {
     print("this time is \(time)")
   }
   goToWork(at: "08:00")
    
   // 默认参数值,c++也可以设置默认值，只是c++默认参数值必须在最右边，swift因为有参数标签不限制，所以省略了参数标签，c++的问题就需要特别注意
   // 因为c++调用函数参数是对号入座的，
   func check(name: String = "nobody", age: Int, job:String="none"){
     print("name=\(name),age=\(age),job=\(job)")
   }
   check(name:"Rose",age:18)
    
   // 如果只想给middle赋值，这里的middle不可以省略标签
   func test(_ first: Int = 10, middle: Int, _ last: Int = 30) {
       print("first=\(first),middle=\(middle),last=\(last)")
   }
   test(middle: 20)
   ```

5. 可变参数

   + 一个函数最多只能有1个可变参数
   + 紧跟在可变参数后面的参数不能省略参数标签

   ```swift
   // 一个函数最多只能有1个可变参数
   // 紧跟在可变参数后面的参数不能省略参数标签
   func sum(_ numbers: Int...) -> Int {
     var total = 0
     for number in numbers {
       total += number
     }
     return total
   }
   sum(10,20,30,40)
   ```

## print函数

```swift
// public func print(_ items:Any...,separator:String=" ",terminator:String="\n") 接收多个参数，这些参数默认以" "连接，结果是\n
print("1","2","3") //1 2 3\n
print("1","2","3",separator:"_",terminator:"")//1_2_3
```

##输入输出函数(In-Out Parameter)

1. 可以用inout定义一个输入输出参数: 可以在函数内部修改外部实参的值
2. 可变参数不能标记为inout
3. inout参数不能有默认值
4. inout参数的本质是地址传递(引用传递)
   1. 如果传递给inout
5. inout只接受可以被多次赋值的

```swift
var number = 10
func add(_ num: inout Int) {
   num = 20
}
add(&number) // number不能是let，不能被多次赋值
print(number) // 20，
```

### 汇编部分

1. callq 后面跟着函数地址、leaq用于做地址传递用
2. %开头的是寄存器,  
3. leaq   0x10da(%rip),%rdi  // (将0x10da+rip地址的和赋值给rdi,所以rdi存储的是地址值)
   1. rip存储的是指令的地址，存的内容是下一个汇编指令的前面标记的地址
   2. CPU要执行的下一条指令地址就存储在rip中
4. movq  &0x14,(%rdi)  //（将16进制的14赋值给rdi所存储地址所在的空间,()找到地址指向的空间）
5. movq  -ox30(%rbp),%rdi  //将rbp-0x30的地址所在空间的值赋值给%rdi

###inout的本质

1. inout本质还是引用传递，传递的是地址值，只是是谁的地址值要看情况
2. 结构体的地址值就是它第一个属性的地址值

```swift
struct Shape {
    var width: Int
    var side: Int {
    
        willSet {
            print("willSet",newValue)
        }
        didSet {
            print("didSet",oldValue,side)
        }
    }
    var girth: Int {
        set {
            width = newValue / side
            print("setGirth",newValue)
        }
        get {
            print("getGirth")
            return width * side
        }
    }
    func show() {
        print("width=\(width), side=\(side), girth = \(girth)")
    }
}

func test(_ num: inout Int) {
    
    num = 20
}
var s = Shape(width:10, side:4)
test(&s.width) 
s.show() // getGirth    width=20, side=4, girth = 80
// 主要汇编
/*
    0x10000103c <+108>: leaq   0x71b5(%rip), %rdi        ; SwiftTest.s : SwiftTest.Shape
    0x100001043 <+115>: callq  0x100001d90               ; SwiftTest.test(inout Swift.Int) -> () at main.swift:41
*/
/*
     0x71b5(%rip)是一个全局地址，从注释可看出是s，将s这个地址值作为参数传递给了test函数，(因为结构体的地址值就是它第一个属性的地址值) 
*/


print("-------------")
test(&s.side)
s.show()  // willSet 20      didSet 4 20      getGirth     width=20, side=20, girth = 400
// 主要汇编
/*
   0x10000131d <+93>:  movq   0x6edc(%rip), %rax        ; SwiftTest.s : SwiftTest.Shape + 8
    0x100001324 <+100>: movq   %rax, -0x28(%rbp)
    0x100001328 <+104>: leaq   -0x28(%rbp), %rdi
   0x10000132c <+108>: callq  0x100001d90               ; SwiftTest.test(inout Swift.Int) -> () at main.swift:41
    0x100001331 <+113>: movq   -0x28(%rbp), %rdi
    0x100001335 <+117>: leaq   0x6ebc(%rip), %r13        ; SwiftTest.s : SwiftTest.Shape
    0x10000133c <+124>: callq  0x100001430               ; SwiftTest.Shape.side.setter : Swift.Int at main.swift:14
*/
/*
     先调用了test，再调用了setter方法
     movq   0x6edc(%rip), %rax： 将某个全局变量的内容赋值给rax，从后面注释可看到这个全局变量是s+8即s的第二个成员side，这句意思是将side里的内容赋值给rax，
     movq   %rax, -0x28(%rbp): rax又赋值给了一个局部变量
     leaq   -0x28(%rbp), %rdi： 将局部变量的地址值赋值给rdi
     调用test修改值，改完后将这个局部变量地址的内容赋值给rdi
     rdi又作为函数参数给了setter，
*/
// setter主要汇编
/*
    0x100001459 <+41>: movq   %rdi, -0x20(%rbp)
    0x10000145d <+45>: movq   %r13, -0x28(%rbp)
    0x100001461 <+49>: movq   %rax, -0x30(%rbp)
    0x100001465 <+53>: callq  0x100001490               ; SwiftTest.Shape.side.willset : Swift.Int at main.swift:16
    0x10000146a <+58>: movq   -0x28(%rbp), %rax
    0x10000146e <+62>: movq   -0x20(%rbp), %rcx
    0x100001472 <+66>: movq   %rcx, 0x8(%rax)
    0x100001476 <+70>: movq   -0x30(%rbp), %rdi
    0x10000147a <+74>: movq   %rax, %r13
    0x10000147d <+77>: callq  0x1000015b0               ; SwiftTest.Shape.side.didset : Swift.Int at main.swift:19
*/
/*
  setter方法里面会调用willset 
  在willset和didset之间代码就是真正修改side内容的代码，改完之后调用didSet
  movq   -0x28(%rbp), %rax：打印rax会发现他是s的地址值，
  movq   -0x20(%rbp), %rcx:
   movq   %rcx, 0x8(%rax): 打印rcx会发现是20的那个值，把这个值存储进rax的第二个成员side
*/
// test函数内部是不可能调用willset和didset，所以弄一个局部变量，test修改这个局部变量再调用setter真正修改side的值，能触发willSet和didSet


print("-------------")
test(&s.girth)
s.show()  //  getGirth       setGirth 20       getGirth       width=5, side=4, girth = 20
// 主要汇编
/*
      0x100001322 <+258>: callq  0x1000016f0               ; SwiftTest.Shape.girth.getter : Swift.Int at main.swift:30
    0x100001327 <+263>: movq   %rax, -0x28(%rbp)
    0x10000132b <+267>: leaq   -0x28(%rbp), %rdi
    0x10000132f <+271>: callq  0x100001d90               ; SwiftTest.test(inout Swift.Int) -> () at main.swift:41
    0x100001334 <+276>: movq   -0x28(%rbp), %rdi
    0x100001338 <+280>: leaq   0x6eb9(%rip), %r13        ; SwiftTest.s : SwiftTest.Shape
    0x10000133f <+287>: callq  0x100001840               ; SwiftTest.Shape.girth.setter : Swift.Int at main.swift:26
*/
/*
  由于s.girth是计算属性，没有自己的内存地址
  从汇编看先调用了girth.getter方法，然后调用了test，掉完test再调用了girth.setter
  先调用setter方法，方法的返回值都存放在rax上，rax又赋值给 -0x28(%rbp)，-0x28(%rbp)是函数的栈空间，函数的栈空间返回时rbp--rsp之间，所以是当前的函数的栈空间(main函数的一个栈空间即main函数的一个局部变量) 
  leaq   -0x28(%rbp), %rdi ： 将局部变量的地址值给rdi，说明将局部变量的地址值给了test，test方法里面会通过地址值修改内容
  调用完test后，看到汇编 movq   -0x28(%rbp), %rdi： 将局部变量里存储的内容给rdi，注意mov和lea的区别
  这个rdi又传给了setter方法

  调用getter得到返回值，将返回值放在一个临时的空间，调用test传递的地址值就是这个临时的空间，调用完后临时的空间就被更改为新的值，改完新值后会调用计算属性girth的set方法，新的值作为newValue传递
*/
```

####总结

1. 如果实参有物理内存地址，且没有设置属性观察器
   1. 直接将实惨的内存地址传入函数(实惨进行引用传递)
2. 如果实参是计算属性或者设置了属性观察器
   1. 采取了Copy In Copy Out的做法
   2. 调用该函数时，先复制实参的值，产生副本[get]
   3. 将副本的内存地址传入函数(副本进行引用传递),在函数内部可以修改副本的值
   4. 函数返回后，再将副本的值覆盖实参的值[set]

##函数重载

+ 规则

  1. 函数名相同
  2. 参数个数不同||参数类型不同||参数标签不同

+ 返回值类型有无不同不构成函数重载

+ 默认参数值和函数重载一起使用产生二义行时，编译器并不会报错(在c++中会报错)

+ 可变参数、省略参数标签、函数重载一起使用产生二义性时，编译器有可能报错

  ```swift
  // 这段放开就会报错
  //func sum(v1:Int, v2:Double) -> Int {
  //    v1+Int(v2)+1
  //}
  func sum(_ v1:Int, _ v2:Int) -> Int {
    v1+v2
  }
  //func sum(v1:Int,v2:Int,v3:Int=30) -> Int {
  //  v1+v2+v3
  //}
  func sum(_ v1:Int,_ v2:Int,_ v3:Int=30) -> Int {
    v1+v2+v3
  }
  
  func sum(_ v1:Int...) -> Int {
      v1.first ?? 0
  }
   
  print(sum(10,20)) // 30
  ```

## 内联函数

1. 如果开启了编译器优化(Release模式默认会开启优化),编译器会自动将某些函数变成内联函数
   1. Build Settings 下搜索optimization会看到Swift Complier， 
2. 内联就是将函数调用展开成函数体,(即调用一个函数时，是将整个函数体的代码拿出来执行，减少了函数调用开销，)
3. 哪些函数不会内联
   1. 函数体比较长
   2. 递归调用
   3. 包含动态派发
   4. ...
4. @inline
   1. 在Release模式下，编译器已经开启优化，会自动决定哪些函数需要内联，因此没必要使用@inline

```swift
// 永远不会被内联(即使开启联编译器优化)
@inline(never) func test() {
   print("test")
}
// 开启联编译器优化后，即使代码很长，也会被内联(递归调用函数、动态派发的函数除外)
@inline(__always) func test(){
  print("test")
}
```

## 函数类型

1. 每一个函数都是有类型的，函数类型由形式参数类型、返回值类型组成

   ```swift
   func test() {}  // () -> Void 或者 () -> ()
   func sum(a:Int,b:Int) -> Int {
     a+b
   } // (Int,Int) -> Int
   // 定义变量
   var fn:(Int,Int) -> Int = sum  // 将函数类型赋值给变量
   fn(2,3) // 调用时不需要参数标签
   ```

2. 函数类型作为函数参数

   ```swift
   func sum(a:Int,b:Int) -> Int {
     a+b
   }
   func difference(a:Int,b:Int) -> Int {
     a-b
   } 
   func printResult(_ mathFn:(Int,Int)->Int,_ a:Int,_ b:Int) {
     print("Result:\(mathFn(a,b))")
   }
   printResult(sum,5,2) // Result:7
   printResult(difference,5,2) // Result:3
   ```

3. 函数类型作为返回值，返回值是函数类型的函数，叫做高阶函数

## typealias

1. 用来给类型起别名

   ```swift
   typealias Byte = Int8
   typealias Date = (year:Int,month:Int,day:Int)
   typealias IntFn = (Int,Int)->Int
   ```

2. 按照Swift标准库的定义，Void其实就是空元祖

   ```swift
   typealias Void = ()
   ```

## 嵌套函数

1. 将函数定义在函数内部

# 枚举

```swift
enum Direction {
   case north
   case south
   case east
}
var dir = Direction.north
dir = .south
```

## 关联值

有时会将枚举的成员值跟其他类型的关联存储在一起，会非常有用

```swift
enum Score {
  case point(Int)
  case grade(Character)
  case digit(year:Int,month:Int,day:Int)
}
var score = Score.point(96)
score = .grade("A")
 
 
switch score {
  case let .point(i):
      print(i,"points")
  case let .grade(i):
      print("grade",i)
  case .digit(let year,let month,let day):
      print("let 写前面表示year、month、day都是let，也可以分开写")
}
```

## 原始值

1. 枚举成员可以使用相同类型的默认值预先关联，这个默认值叫做原始值
2. 如果枚举的原始值类型是Int(0\1\2递增)、String(即是case的值比如notrh=“north”),Swift会自动分配原始值
3. 原始值是什么并不会影响这个枚举的大小

```swift
enum Grade: String {
  case perfect = "A"
  case great = "B"
  case good = "C"
}
```

## 递归枚举

枚举里的一个case关联枚举，需要加上indirect修饰

```swift
indirect enum ArithExpr {
  case number(Int)
  case sum(ArithExpr,ArithExpr)
}
```

## memoryLayout

获取类型占用的内存大小

```swift
// 关联值、原始值的区别
// 如果是关联值，是将内容直接存储进内存里
// 如果是原始值，值是固定的，所以不管什么类型，都只是将每个值标个序号(只占一个字节)，调用x.rawValue才会真正拿到默认值
//memoryLayout.size      // 实际用到的空间大小
//memoryLayout.stride    // 分配占用的空间大小
//memoryLayout.alignment // 对齐参数
 
enum Password {
   case number(Int,Int,Int,Int) // 32
   case other  // 1
}
var pwd = Password.number(5,6,7,8)
pwd = .other
 
print(MemoryLayout<Password>.size) // 33
print(MemoryLayout<Password>.stride)    // 40
print(MemoryLayout<Password>.alignment)  // 8
 
 
enum Season : String {
   case spring = "spring",sumer = "sumer",autumn = "autumn",winter = "winter"
}
var s = Season.spring
 
print(MemoryLayout<Season>.size)       // 1
print(MemoryLayout<Season>.stride)     // 1
print(MemoryLayout<Season>.alignment)  // 1
```

# 可选项(Optional)

1. 可选类型允许将值设置为nil
2. 在类型名称后面加个问好?来定义一个可选项
3. 通过字典拿到的值是可选项，通过数组拿到的就是值，数组越界会崩溃

```swift
var age: Int   // 没有初始值
var kage: Int?  // 默认为nil
 
 
// 强制解包
var num = Int("123")
if num != nil {
   print("转换成功:\(num!)")
} else {
   print("转换失败")
}
 
 
// 可选项绑定
if let number = Int("123") { // number 作用域只在这个大阔号里
   print("转换成功:\(number)")
} else {
   print("转换失败")
}
 
 
// 同时成立判断,相当于first有值解包成功&&second有值解包成功&&first < second // && second < 100的话打印...  对于可选项绑定的并且用，不能用&&
if let first = Int("4"),
   if second = Int("42"),
   first < second && second < 100 {
   print("\(second) < \(second) < 100")
}
 
 
// while循环中使用可选项绑定
// 遍历数组,将遇到的正数都加起来，如果遇到负数或者非数字，停止遍历
var strs = ["10","20","abc","-20","30"]
var index = 0
var sum = 0
while let num = Int(strs[index]),num>0 {  // 并且
   sum += num
   index += 1
}
 
 
// ?? 与 if let配合使用
let a: Int? = nil
let b: Int? = 2
if let c = a ?? b { // 类似于 if a!=nil||b!=nil
   print(c)
}
```

##强制解包

1. 可选项是对其他类型的一层包装，可以将它理解为一个盒子
2. 如果为nil，那么是个空盒子
3. 如果不为nil，那么盒子里装的是:被包装类型的数据
4. 如果要从可选项中取出被包装的数据(将盒子里装的东西取出来),需要使用感叹号!进行强制解包
5. 如果对值为nil的可选项(空盒子)进行强制解包，将会产生运行时错误

##可选项绑定

1. 可以使用可选项绑定来判断可选项是否包含值
2. 如果包含值就自动解包，把值赋给一个临时的常量(let)或者变量(var),并返回true,否则返回false
3. 可选项绑定的"并且"用','表示，不能用'&&'

##空合并运算符??

1. a??b  // 如果a不为nil，返回a，如果a为nil，返回b
2. a 是可选项，b可以是可选项也可以不是可选项，a跟b的存储类型必须相同
3. 如果b不是可选项，返回a时会自动解包

## guard

1. 当guard条件为false时，就会执行大括号里的代码
2. 当guard条件为true时，就会跳过guard语句
3. 当使用guard语句进行可选项绑定时，绑定的常量(let)、变量(var)也能在外层作用域中使用

```swift
guard 条件 else {
    
   退出当前作用域// return、break、continue、throw、error
}
 
 
// 使用
func login(_ info:[String:String]) {
   guard let username = info["username"] else {
      print("请输入用户名")
      return
   }
   guard let password = info["password"] else {
      print("请输入密码")
      return
   }
   // 进行登录
   print("用户名:\(username)","密码:\(password)","登陆ing")
}
```

## 隐式解包

1. 在某些情况下，可选项一旦被设定值之后，就会一直拥有值
2. 在这种情况下，可以去掉检查，也不必每次访问的时候都进行解包，因为它能确定每次访问的时候都有值
3. 可以在类型后面加个感叹号！定义一个隐式解包的可选项

```swift
let num1: Int! = 10  // 虽然是盒子，但是是！，所以会自动解包
let num2: Int = num1 // 隐式解包了 Int类型
let num3 = num1  // Int? 类型

let num4: Int? = 10  // 虽然是盒子，但是是！，所以会自动解包
let num5: Int = num4  // 会报错
```

##可选项的字符串插值

可选项在字符串插值或者直接打印时，编译器会发出警告

```swift
var age: Int? = 10   // 10 或 nil
print("My age is \(age)") // 会有警告 My age is Optional(10) 或 My age is nil
//消除警告方法
print("My age is \(age!)") // My age is 10 或 崩溃
print("My age is \(String(describing: age))")  // My age is Optional(10) 或 My age is nil
print("My age is \(age ?? 0)")  // My age is 10 或 My age is 0
```

## 多重可选项

xcode命令行输入: fr v -R x (可以看到对象的组成结构)

```swift
var num11: Int? = 10
/* num1 = some { 
 *  some = {
 *    _value = 10
 *  }
 * }
*/
var num22: Int?? = num1
var num33: Int?? = 10
print(num22 == num33) // true
 
 
var num1: Int? = nil
/* num1 = none {  // 为none的时候里面的内容无意义
 *  some = {
 *    _value = 0
 *  }
 * }
*/
var num2: Int?? = num1
/* num2 = some {
    some = none { 
 *    some = {
 *      _value = 0
 *    }
 * }
  }
*/
var num3: Int?? = nil
/* num3 = none {
    some = some { 
 *    some = {
 *      _value = 0
 *    }
 * }
  }
*/
print(num2 == num3) // false 重点  如果num2和num3存的value都是1，那么为true，因为自顶向下都是some、some、1
print(num1 == num3) // false 类型不同
(num2 ?? 1) ?? 2  // 2  重点 num2不为nil
(num3 ?? 1) ?? 2  // 1  重点 num3为nil
```

# 汇编

## 窥探内存

1. 打断点→View Memory of 'num' 或者工具栏→Debug下Debug Workflow的View Memory
2. 查看内存地址的小工具: https://github.com/CoderMJLee/Mems （把Mems.Swift拖入项目既可用）
3. 查看普通枚举，发现枚举用了一个字节来存储，存储0、1、2、、、、、
4. 对于关联值，一个字节存储成员值，N个字节存储关联值(N取占用内存最大的关联值)

```swift
// 可以看到用前面24个字节存储内容值
// 第25个字节用来表示是哪一个枚举
enum TestEnum {
  case test1(Int,Int,Int) // 24
  case test2(Int,Int)
  case test3(Int)
  case test4(Bool)
  case test5
}

var e = TestEnum.test1(1,2,3)
// 小端: 高高低低
// 01 00 00 00 00 00 00 00
// 02 00 00 00 00 00 00 00
// 03 00 00 00 00 00 00 00
// 00
// 00 00 00 00 00 00 00
print(Mems.ptr(ofVal:&e)) // 打印地址，再通过View Memory of 'num'或者控制台x/20 0x0000000100008228查看该地址下存储的内容
e = .test2(4,5)
// 04 00 00 00 00 00 00 00
// 05 00 00 00 00 00 00 00
// 00 00 00 00 00 00 00 00
// 01
// 00 00 00 00 00 00 00
e = .test3(6)
e = .test4(true)
e = .test5
 
 
MemoryLayout<TestEnum>.size       // 25
MemoryLayout<TestEnum>.stride     // 32
MemoryLayout<TestEnum>.alignment  // 8
 
 
// 只有一个值的枚举,并不会占用内存
enum TestEn {
  case test
}
var t = TestEn.test
MemoryLayout<TestEn>.size       // 0
MemoryLayout<TestEn>.stride     // 1
MemoryLayout<TestEn>.alignment  // 1
// 同理只有一个值的关联枚举,并不需要额外一个字节标示是哪个枚举
enum TestE {
  case test(Int)
}
var te = TestE.test
MemoryLayout<TestE>.size       // 8
MemoryLayout<TestE>.stride     // 8
MemoryLayout<TestE>.alignment  // 8
```

## 程序的本质

1. 存放在硬盘的程序\软件  ----装载---->内存(全是0101的数据) <---读写----> CPU ----> 控制 ----->计算机(显示器、音响、话筒、其他设备)

2. CPU(寄存器(信息存储)、运算器(运算处理)、控制器)

3. 寄存器与内存

   1. 通常，CPU会先将内存中的数据存储到寄存器中，然后再对寄存器中的数据进行运算

      ![寄存器与内存](/Swift入门到精通笔记/寄存器与内存.png)

##编程语言的发展

1. 机器语言
   1. 由0和1组成
2. 汇编语言
   1. 用符号代替来0和1，比机器语言便于阅读和记忆
3. 高级语言
   1. C\C++\Java\JavaScript\Python等，更接近人类自然语言
4. 高级语言 ----编译---> 汇编语言 ----编译---> 机器语言 ----运行---> 计算机
   1. 汇编语言和机器语言一一对应，每一条机器指令都有与之对应的汇编指令
   2. 汇编语言可以通过编译得到机器语言，机器语言可以通过反汇编得到汇编语言
5. 汇编语言的种类
   1. 8086汇编(16bit)
   2. x86汇编(32bit)
   3. x64汇编(64bit)
   4. ARM汇编(嵌入式、移动设备)
   5. .....
6. x86、x64汇编根据编译器的不同，有2种书写格式
   1. Intel    ：Windows派系
   2. AT&T  ：Unix派系
7. 作为iOS开发工程师，主要的汇编语言是
   1. AT&T汇编 -----> iOS模拟器
   2. ARM汇编 -----> iOS真机设备

##常见汇编指令

1. jmp是跳转，call是执行某个函数，call一般和return配合使用，jmp则不能用
2. 有rax，rip，eax，ecx(e开头都是32位的，r开头的是8个字节)
3. 指令mov、lea后面跟着数字决定来操作数长度

![常见汇编指令](/Swift入门到精通笔记/常见汇编指令.png)

## lldb常用指令

1. thread step-over、next、n : 单步运行，把子函数当作整体一步执行(源码级别)
2. thread step-in、step、s : 单步运行，遇到子函数会进入子函数(源码级别)
3. thread step-inst-over、nexti、ni : 单步运行，把子函数当作整体一步执行(汇编级别)
4. thread step-inst、stepi、si : 单步运行，遇到子函数会进入子函数(汇编级别)
5. thread step-out、finish : 执行执行完当前函数的所有代码,返回到上一个函数(遇到断点会卡住)
6. ![lldb常用指令](/Swift入门到精通笔记/lldb常用指令.png)

##汇编看闭包

1. 汇编: xorl %ecx, %ecx  // 异或，ecx异或ecx为0，再将这个0赋值给ecx
2. 汇编: movq %rcx, (%rax) // (%rax)指取出rax里面存的值
3. 汇编: addq $0x10, %rdx // 将rdx的地址+0x10，再赋值给rdx
4. 没有捕获严格意义上不算闭包

# 值类型和引用类型(结构体和类)

##结构体

1. 在Swift标准库中，绝大多数的公开类型都是结构体，而枚举和类只占很小一部分

2. Bool、Int、Double、String、Array、Dictionary都是结构体

3. 所有的结构体都有一个或多个编译器自动生成的初始化器(保证所有成员都有初始值)(initializer,初始化方法，构造器，构造方法)

4. 一旦在定义结构体时自定义类初始化器，编译器就不会再帮它自动生成其他初始化器

   ```swift
   struct Date {
     var year: Int = 1
     var month: Int
     var day: Int
   }
   var date = Date(year:2019,month:6,day:23) //这些成员叫存储属性
   var date2 = Date(month: 1, day: 2)
   ```

##类

1. 类的定义和结构体类似，但编译器并没有为类自动生成可以传入成员值的初始化器
2. 如果这些成员都有默认值，会只生成一个无参初始化器
3. 类自带16个字节，存储类型信息和引用计数

## 类和结构体的区别

1. 结构体是值类型(枚举也是值类型)，类是引用类型(指针类型)

2. 查看汇编可看到结构体没有调用alloc，类有调用alloc

   ![类和结构体的区别](/Swift入门到精通笔记/类和结构体的区别.png)

### 对象的堆空间申请过程

1. 在Swift中，创建类的实例对象，要向堆空间申请内存，大概流程如下
   1. Class.__allocating_init()
   2. libswiftCore.dylib:_swift_allocObject_
   3. libswiftCore.dylib: swift_slowAlloc
   4. libsystem_malloc.dylib:malloc
2. 在Mac、iOS中的malloc函数分配的内存大小总是16的倍数
3. 通过class_getInstanceSize可以得知类的对象真正使用的内存大小：class_getInstanceSize(Point.self) 或class_getInstanceSize(type(of:p))
4. malloc_size()// 或者指针指向的堆空间占用的空间使用:

## 引用类型

1. 引用赋值给var、let或者给函数传参，是将内存地址拷贝一份

2. 类似于制作一个文件的替身(快捷方式、链接)，指向的是同一个文件。属于浅拷贝(shallow copy)

   ![引用类型](/Swift入门到精通笔记/引用类型.png)

3. 汇编：rax、rdx一般用于做函数返回值使用
   1. rdi、rsi、rdx、rcx、r8、r9等寄存器常用于存放函数参数，
   2. 如果以上都不够放参数，rsp、rbp用于栈操作，会把剩余的函数参数存放在rsp、rbp里
   3. rip作为指令指针
   4. register read rax: 获取rax的地址值
   5. 栈分配的地址先分配的更高，后分配的地址更低
   6. 内存地址格式为：0x10(%rax)，一般是堆空间
   
   ```swift
   func testReferenceType() {
      class Size {
        var width: Int
        var height: Int
        init(width:Int,height:Int) {
           self.width = width
           self.height = height
        }
      }
      var s1 = Size(width:10,height:20)
      var s2 = s1
      s2.width = 11
      s2.height = 22
   }
      
   对应部分汇编：
   // 凡是__allocating_init就可以知道这个函数里面是alloc init功能
   callq 0x100000f50  ; __allocating_init(width: ...)
   // rax一般作为返回值使用，所以这里rax存储的是初始化后的对象
   // -0x10(%rbp)是一个局部变量，所以它就是s1的指针变量了
   movq  %rax,  -0x10(%rbp)
   movq  %rax,  %rdi
   // -0x60(%rbp)是另一个局部变量，所以它就是2的指针变量了
   movq  %rax,  -0x60(%rbp)
   callq 0x10005dca  ; symbol stub for: swift_retain
   .....
   // 将s2的地址值给rax
   movq  -0x60(%rbp), %rax
   // 将0xb(10位数的11) 给rax移动16个字节位置(即字节的第一个成员属性)
   movq  $0xb, 0x10(rax)
   ```

## 值类型

1. 类无论在哪里创建，这个对象的内存都在堆空间，指针变量地址会根据情况变化
2. 值类型赋值给var、let或者给函数传参，是直接将所有内容拷贝一份
3. 类似于对文件进行copy、paste操作，产生了全新的文本副本。属于深拷贝
4. 汇编eax其实就是rax，上一个指令是eax，下一个指令rax，他们2个就是代表同一个东西
5. 汇编规律，内存地址格式为:0xxxxx(%rip), rip加上一个很大的值，一般是全局变量(全局区、数据段)，程序运行过程中rip的值不会变，
6. 汇编规律，内存地址格式为:-0x78(%rbp), rbp减掉一个值，一般是局部变量(栈空间)，rbp每次调用时候的值就有可能不同
7. 字符串、数组都是值类型
8. 在Swift标准库中，为了提示性能，String、Array、Dictionary、Set采取了Copy On Write技术
   1. Copy On Write: 如果值不做更改，就不进行深拷贝，如果做了修改操作，就进行深拷贝
   2. 自定义的结构体是没有这个技术的
9. 不需要修改的，尽量定义为let

```swift
struct Point {
  var x: Int
  var y: Int
}
func test() {
  var p1 = Point(x:10,y:20)
  var p2 = p1
  p2.x = 11
  p2.y = 22
  // 问p1的x和y是多少？
  // 答: 10 20
 
 
  p1 = Point(x:100,y:200)
  // p1的地址并不会变化，只是地址里的值会更改为100，200
}
 
 
// Copy On Write
var s1 = "Jack"
var s2 = s1  // 如果s1和s2的值一样，那么s2的地址和s1的地址就是一样的
s2.append（"_Rose"） // 2个不一样开始做了深拷贝，s2做深拷贝
```

##枚举、结构体、类都可以定义方法

1. 一般把定义在枚举、结构体、类内部的函数，叫做方法
2. 方法并不占用对象的内存
3. 方法的本质就是函数
4. 方法、函数都存放在代码段
5. 方法中的变量在栈里
6. 代码段放在最前面地址最小，再接着是全局变量，然后是堆空间，最后栈空间，栈空间的地址一般都特别特别大，

##引用类型的赋值操作

```swift
class Size {
    var width: Int
    var height: Int
    init(width: Int, height: Int) {
        self.width = width
        self.height = height
    }
}
 
var s1 = Size(width: 10, height: 20)
s1 = Size(width: 11, height: 22)
// s1的地址值并不会变，但是s1中存的对象地址会变
```

##值类型、引用类型的let

```swift
let p = Point(x: 10, y: 20) // Point结构体值类型 // p = Point(x: 11, y: 22) // 不允许 // p.x = 33 // 不允许 // p.x = 44 // 不允许 let s = Size(width: 10, height: 20)// s = Size(width: 11, height: 22) // 不允许s.width = 33 // 允许s.height = 44 // 允许
```

##方法

1. 枚举、结构体、类都可以定义实例方法、类型方法
   1. 实例方法：通过实例调用
   2. 类型方法：通过类型调用，用static或者class关键字定义
2. self在实例方法中代表实例，在类型方法中表示类型

```swift
Class Car {
   static var count = 0
   init() {
      Car.count += 1
   }
   static func getCount() -> Int { count } // count等价于self.count、Car.self.count、Car.count
}
 
let c0 = Car()
let c1 = Car()
let c2 = Car()
print(Car.getCount()) // 3 getCount方法就是类型方法
```

## mutating

1. 枚举和结构体是值类型，默认情况下，值类型的属性不能被自身的实例方法修改 
   1. 在func关键字前加mutating可以允许这种修改行为

```swift
struct Point {
   var x = 0.0, y = 0.0
   mutating func moveBy(deltaX: Double, deltaY: Double) { // 不加mutating会报错
      x += deltaX
      y += deltaY
   }
}
```

## @discardableResult

1. 在func前面加个@discardableResult，可以消除: 函数返回值未被使用的警告

## 下标(subscript)

1. 前端有2个标签<sup>//上标 <sub> //下标 上标下标就是方程式中的上标下标
2. 使用subscript可以给任意类型(枚举、结构体、类)增加下标功能，有些地方也翻译为:下标脚本
3. subscript的语法有点类似于实例方法、计算属性，本质就是方法(函数)
4. subscript中定义的返回值类型决定了
   1. get方法的返回值类型
   2. set方法中newValue的类型
5. subscript可以接受多个参数，并且类型任意,可以设置参数标签
6. subscript可以没有set方法，但必须要有get方法，此时可以省略get{}这个外包装 和计算属性相同(只读计算属性)
7. 下标可以是类型方法

```swift
class Point {
   var x = 0.0, y = 0.0
   subscript(index: Int) -> Double { // 这里的Double说明newValue是double，get返回的会是Double
     set {
       if index == 0 {
          x = newValue
       } else if index == 1 {
          y = newValue
       }
     }
     get {
        if index == 0 {
           return x
        } else if index == 1 {
           return y
        }
        return 0
     }
   }
}
var p = Point()
p[0] = 11.1 // 本质调用set方法
p[1] = 22.2
print(p.x) // 11.1
print(p.y) // 22.2
print(p[0]) // 11.1 本质调用get方法
print(p[1])  // 22.2
```

8. 结构体、类可以作为返回值对比

   ```swift
   struct Point {
     var x = 10,y=10
   }
   class PointManager {
      var point = Point()
      subscript(index: Int) -> Point {
         set { point = newValue }
         get { point }
      }
   }
   var pm = PointManager()
   pm[0].x = 11  // 会走下标的set方法  本质就是 pm[0] = Point(x:11, y:pm[0].y)，如果没有set方法pm[0]由于是值类型，并不会更改到point里面所存储的值，所以会报错
    
    
   // 如果Point是class的话，没有写set方法，pm[0].x = 11也不会报错，此时相当于pm[0]调用了get方法，由于类是引用类型，返回的point其实是一个指针，所以可以更改指针里所指向的x、y的值
   ```

9. 接收多个参数的下标

## 继承

1. 值类型(枚举、结构体)不支持继承，只有类支持继承
2. 没有父类的类，称为: 基类
   1. Swift并没有像Oc、java那样的规定: 任何类最终都要继承自某个基类
3. 子类可以重写父类的下标、方法、属性(计算属性)，重写必须加上override关键字
   1. 实例下标、方法、属性(计算属性) 允许被子类重写
   2. 被class修饰的类型方法、下标， 允许被子类重写
   3. 被static修饰的类型方法、下标，不允许被子类重写
   4. 子类重写可将重写的方法、下标修饰为static，这样子类的子类不能重写

### 重新实例属性

1. 子类可以将父类的属性(存储、计算)重写为计算属性
2. 子类不可以将父类的属性(存储、计算)重写为存储属性

```swift
class Circle {
  var radius: Int = 0
  var diameter: Int {
     set{
        print("Circle setDiameter")
        radius = newValue / 2
     }
     get {
       print("Circle getDiameter")
       return radius * 2  // 子类通过调用super.diameter调用到这里，radius又会调用到子类的radius的get方法，子类的radius的get会去父类拿到存储属性radius的值
     }
  }
}
class SubCircle: Circle {
   override var radius: Int {
     set{
        print("SubCircle setRadius")
        super.radius = newValue > 0 ? newValue : 0 // 设置父类的存储属性radius的值
     }
     get {
        print("SubCircle getRadius")
        return super.radius  // 获取父类的存储属性radius的值
     }
   }
   override var diameter: Int {
     set{
        print("SubCircle setDiameter")
        super.diameter = newValue > 0 ? newValue : 0
     }
     get {
       print("SubCircle getDiameter")
       return super.diameter  // 调用父类的get方法
     }
  }
}
var circle = SubCircle()
 
 
// SubCircle setRadius
circle.radius = 6
 
 
// SubCircle getDiameter
// Circle getDiameter
// SubCircle getRadius
// 12
print(circle.diameter)
 
 
// SubCircle setDiameter
// Circle setDiameter
// SubCircle setRadius
circle.diameter = 20
 
 
// SubCircle getRadius
// 10
print(circle.radius)
```

### 重写类型属性

1. 被class修饰的计算类型属性，可以被子类重写(class不能修饰存储属性)
2. 被static修饰的类型属性(存储、计算)，不可以被子类重写

###属性观察器

1. 可以在子类中为父类属性(除了只读计算属性、let属性)增加属性观察器
2. 如果子类和父类都有属性观察器，调用子类.属性 = 20，那么子类 willSet.. → 父类 willSet → 父类 didSet → 子类 didSet
3. 父类是计算属性，子类也可以增加属性观察器

```swift
class Circle {
  var radius: Int {
     set{
        print("Circle setRadius",newValue)
     }
     get {
       print("Circle getRadius")
       return 20
     }
  }
}
class SubCircle: Circle {
   override static var radius: Int {
     willSet{
        print("SubCircle willSetRadius",newValue)
     }
     didSet {
        print("SubCircle didSetRadius",oldValue,radius) // radius会调用到父类的get方法
     }
   }
}
 
 
// Circle getRadius   // 这里是调用oldValue产生的打印，因为是oldValue，所以会在willSet之前先拿到原来的值
// SubCircle willSetRadius 10
// Circle setRadius 10
// Circle getRadius
// SubCircle didSetRadius 20 20
SubCircle.radius = 10
```

### final

1. 被final修饰的方法、下标、属性，禁止被重写
2. 被final修饰的类，不能被继承

## 多态原理

1. Oc利用rutime实现
2. C++利用虚表(虚函数表)
3. Swift类似于C++的虚表
4. 类的前8个字节放着类型信息;类型信息所指向地址放着方法地址，没重写的父类方法地址
5. 看类型信息所存内容的地址可知道它放在全局区
6. 同一个类的多个实例前8个字节都是一样的

```swift
// 类定义方法后生成的汇编会非常多， call  不是固定地址值,因为运行的时候才能确定的
// 调用类方法会找到这类的前面8个值，再找到类型地址所指向地址，找到这个地址+偏移量后得到方法地址，
// 结构体定义方法后生成的汇编就比较少，call 0x固定地址值
```

## 初始化器

1. 类、结构体、枚举都可以定义初始化器
2. 类有2种初始化器：指定初始化器(designated initializer)、便捷初始化器(convenience initializer)
   1. 每个类至少有一个指定初始化器，指定初始化器是类的主要初始化器
   2. 默认初始化器总是类的指定初始化器 ，当你没写新的指定初始化器，会自带默认初始化器，一旦自己写了新的指定初始化器，默认初始化器就自动消失，不能再用了，但是当你只写了一个新的便捷初始化器，默认初始化器依然存在，依然可用。
   3. 类偏向于少量指定初始化器，一个类通常只有一个指定初始化器
   4. 类可以多定义便捷初始化器，少定义指定初始化器
3. 初始化器的相互调用规则
   1. 指定初始化器必须从它的直系父类调用指定初始化器（不能自己的指定调用自己的指定），并且调用顺序要写在子类自定义的属性后面，并且没调用父类的指定初始化器前，不能使用从父类继承过来的属性
   2. 便捷初始化器必须从相同的类里调用另一个初始化器
   3. 便捷初始化器最终必须调用一个指定初始化器

![初始化器简单图](/Swift入门到精通笔记/初始化器简单图.png)

![初始化器复杂图](/Swift入门到精通笔记/初始化器复杂图.png)

### 两段式初始化

1. Swift在编码安全方面是煞费苦心，为了保证初始化过程的安全，设定了两段式初始化、安全检查

2. 两段式初始化

   1. 第一阶段: 初始化所有存储属性

      1. 外层调用指定\便捷初始化器
      2. 分配内存给实例，但未初始化
      3. 指定初始化器确保当前类定义的存储属性都初始化
      4. 指定初始化器调用父类的初始化器，不断向上调用，形成初始化器链

      ```swift
      class Person {
          
          var age: Int
          init(age: Int) {
              
              print(self.age) // 会报错
              self.age = age
          }
      }
      ```

   2. 第二阶段：设置新的存储属性值

      1. 从顶部初始化器往下，链中的每一个指定初始化器都有计划进一步定制实例
      2. 初始化器现在能够使用self(访问、修改它的属性，调用它的实例方法等等)
      3. 最终，链中任何便捷初始化器都有机会定制实例以及使用self

3. 安全检查

   1. 指定初始化器必须保证在调用父类初始化器之前，其所在类定义的所有存储属性都要初始化完成
   2. 指定初始化器必须先调用父类初始化器，然后才能为继承的属性设置新值
   3. 便捷初始化器必须先调用同类中的其他初始化器，然后再为任意属性设置新值
   4. 初始化器在第一阶段初始化完成之前，不能调用任何实例方法、不能读取任何实例属性的值，也不能引用self
   5. 直到第一阶段结束，实例才算完全合法

### 自动继承

1. 如果子类没有自定义任何指定初始化器，它会自动继承父类所有的指定初始化器，否则不会自动继承
2. 如果子类提供类父类所有指定初始化器的实现(要么通过1继承，要么重写)
   1. 子类自动继承所有的父类便捷初始化器
3. 就算子类添加了更多的便捷初始化器，这些规则仍然适用
4. 子类以便捷初始化器的形式重写父类的指定初始化器，也可以作为满足规则2的一部分

```swift
class Person {
    
    var age: Int
    init(age: Int) {
        self.age = age
    }
    convenience init() {
        self.init(age: 0)
    }
    init(hhh: Int) {
        self.age = 0
    }
}
 
class Student: Person {
    
   var score: Int = 0
    
    convenience override init(age: Int) {
        
        self.init()
        self.score = score
        self.age = 10
    }
   convenience init(age: Int, score: Int) {
    
        self.init(age: age)
        self.score = score
        self.age = 10
    }
}

Student(age: 2)
Student()
Student(age: 3, score: 4)
Student(hhh: 2)
```

### 重写初始化器

1. 当重写父类的指定初始化器时，必须加上override(即使子类的实现是便捷初始化器)
2. 子类可以将父类的指定初始化器重写写便捷初始化器
3. 如果子类写类一个匹配父类便捷初始化器的初始化器(不管是指定还是便捷)，不用加上override(便捷初始化器不能被子类调用，所以严格说不算重写)

```swift
class Person {
   var width: Int
   var height: Int
   // 指定初始化器
   init(width: Int, height: Int) {
      self.width = width
      self.height = height
   }
   convenience init() {
      self.init(width: 0,height: 0)
   }
}
class Student: Person {
   var score: Int
   init(width: Int,height: Int, score: Int) {
       self.score = score // 指定初始化器确保当前类定义的存储属性都初始化
       // 指定初始化器必须从它的直系父类调用指定初始化器
       super.init(width: width,height: height)
       // 可以开始使用self(访问、修改它的属性，调用它的实例方法等等)
       self.width = 10
   }
   override convenience init(width: Int, height: Int) {
      self.init(width: width,height: height,score: 0)
   }
 
 
   init() { // 如果子类写类一个匹配父类便捷初始化器的初始化器(不管是指定还是便捷)，不用加上override
 
 
      self.score = 0
      super.init(width: 0, height: 0)
   }
}
```

### required

1. 用required修饰指定初始化器，表明其所有子类都必须实现该初始化器(通过继承或者重写实现)
2. 如果子类重写了required初始化器，也必须加上required，不用加override

### 属性观察器

1. 父类的属性在它自己的初始化器中赋值不会触发属性观察器，但在子类的初始化器中赋值会触发属性观察器

### 可失败初始化器

1. 类、结构体、枚举都可以使用init?定义可失败初始化器
2. 不允许同时定义参数标签、参数个数、参数类型相同的可失败初始化器和非可失败初始化器
3. 可以用init!定义隐式解包的可失败初始化器
4. 可失败初始化器可以调用非可失败初始化器，非可失败初始化器调用可失败初始化器需要进行解包
5. 如果初始化器调用一个可失败初始化器导致初始化失败，那么整个初始化过程都失败，并且之后的代码都停止执行
6. 可以用一个非可失败初始化器重写一个可失败初始化器，但反过来是不行的(协议反过来也可以)

```swift
class Person {
    
    var name: String = ""
    var age: Int = 0
    convenience init?(name: String) { // 定义可失败初始化器
        
        self.init() // 可失败初始化器调用非可失败初始化器
        if name.isEmpty {
            return nil
        }
        self.name = name
    }
    init!(age: Int) { // 隐式解包的可失败初始化器
        if age == 0 {
            return nil
        }
        self.age = age
    }
    
    // 非可失败初始化器调用可失败初始化器
    convenience init() {
        
        self.init(age: 2) // 隐式解包
        self.name = ""
    }
    
    // 非可失败初始化器调用可失败初始化器
    convenience init(name: String, age: Int) {
        
        self.init(name: "")!  // 强制解包
    }
}
 
class Student: Person {
    
    override init(age: Int) { // 非可失败初始化器重写一个可失败初始化器
        
        super.init(age: age)
    }
}
```

### 反初始化器(deinit)

1. deinit叫做反初始化器，类似于C++的析构函数、OC中的dealloc方法 
2. 当类的实例对象被释放内存时，就会调用实例对象的deinit方法
3. deinit不接受任何参数，不能写小括号，不能自行调用 
4. 父类的deinit能被子类继承
5.  子类的deinit实现执行完毕后会调用父类的deinit

```swift
class Person {
   deinit {
      print("Person对象销毁了")
   }
}
class Student: Person {
   deinit {
print("Student对象销毁了")
   }
}
func test() {
   var stu = Student()
}
print("1")
test()
print("2")
// 1
// Student对象销毁了
// Person对象销毁了
// 2
```

# 闭包

## 介绍

### 闭包表达式

1. swift里，可以通过func定义一个函数，也可以通过闭包表达式定义一个函数

```swift
func sum(_ v1: Int, _ v2: Int) -> Int { v1 + v2 }

var fn = {
    (v1: Int, v2: Int) -> Int in
    return v1 + v2
}
//fn(10,20)
//// 闭包格式
//{
//    (参数列表) -> 返回值类型 in
//    函数体代码
//}

// 定义完直接调用
{
    (v1: Int, v2: Int) -> Int in
    return v1 + v2
}(10,20)
```

### 闭包表达式的简写

```swift
func exec(v1: Int, v2: Int, fn:(Int,Int) -> Int) {
  print(fn(v1,v2))
}
// 省略1
exec(v1: 10,v2: 20, fn{
  (v1: Int, v2: Int) -> Int in
  return v1+v2
})
// 省略2
exec(v1: 10,v2: 20, fn{
  v1, v2 in
  return v1+v2
})
// 省略3
exec(v1: 10,v2: 20, fn{
  v1, v2 in v1+v2
})
// 省略4
exec(v1: 10,v2: 20, fn{ $0 + $1}) // $0表示第1个参数，$1第二个参数
// 省略5
exec(v1: 10,v2: 20, fn{+}) // 测试这个不行
```

### 尾随闭包

1. 如果将一个很长的闭包表达式作为函数的最后一个实惨，使用尾随闭包可以增强函数的可读性
2. 尾随闭包是一个被书写在函数调用括号外面(后面)的闭包表达式
3. 如果闭包表达式是函数的唯一实参，而且使用里尾随闭包的语法，那就不需要在函数名后边写圆括号

```swift
func exec(v1: Int, v2: Int, fn:(Int, Int) -> Int) {
    
    print(fn(v1,v2))
}

exec(v1: 10, v2: 20) {
    
    $0 + $1
}
 
func execfn(fn:(Int, Int) -> Int) {
    
    print(fn(1,2))
}

execfn(fn: {$0+$1})

execfn(){$0+$1}

execfn{$0+$1}
```

### 闭包

1. 一个函数和它所捕获的变量\常量环境组合起来，称为闭包
2. (lldb) x/5xg 地址 // 会在控制台打印5组(8(g)个字节一组)该地址里内容
3. 可以把闭包想象成一个类的实例对象，内存在堆空间，捕获的局部变量、常量就是对象的成员，组成闭包的函数就是类内部定义的方法

```swift
func getFn() -> Fn {
   var num = 0 // 局部变量
   func plus(_ i: Int) -> Int {
     num += i    // 内层函数使用里外层变量，就会在堆空间申请内存存储num
     return num
   }
   return plus
}
var fn = getFn()
print(fn(1))  // 1
print(fn(2))  // 3
print(fn(3))  // 6
```

### 自动闭包

1. @autoclosure会自动将20封装成闭包{20}
2. @autoclosure只支持() → T 格式的参数
3. @autoclosure并非只支持最后一个参数 
4. 空合并运算符？？使用了@autoclosure技术
5. 有@autoclosure和无@autoclosure构成了函数重载

```swift
func getFirstPositive(_ v1: Int, _ v2: @autoclosure () -> Int) -> Int {
     
    return v1 > 0 ? v1 : v2()
}
// 会自动将20封装成闭包{20}
getFirstPositive(-4,20)
```

###汇编看闭包

1. 汇编: xorl %ecx, %ecx  // 异或，ecx异或ecx为0，再将这个0赋值给ecx
2. 汇编: movq %rcx, (%rax) // (%rax)指取出rax里面存的值
3. 汇编: addq $0x10, %rdx // 将rdx的地址+0x10，再赋值给rdx
4. 没有捕获严格意义上不算闭包

# 属性

1. Swift中跟实例相关的属性可以分为2大类
   1. 存储属性
      1. 类似于成员变量这个概念
      2. 存储在实例的内存中
      3. 结构体、类可以定义存储属性
      4. 枚举不可以定义存储属性
   2. 计算属性
      1. 本质就是方法(函数)
      2. 不占用实例的内存
      3. 枚举、结构体、类都可以定义计算属性
   3. 属性观察器、计算属性的功能，同样可以应用在全局变量、局部变量身上

```swift
struct Circle {
   // 存储属性
   var radius: Double  // 一个double占8个内存
   // 计算属性
   var dismeter: Double {  // 不存在dismeter这个成员变量
      set {
         radiu = newValue / 2
      }
      get {
         radiu * 2 // 省略了return
      }
   }
}
var c = Circle = 40
print(c.radius) // 20
c.radius = 11
print(c.diameter) // 22
print(MemoryLaout<Circle>.stride)  // 8
```

## 存储属性

```swift
struct Point {
   var x: Int = 11
   var y: Int = 22
    
  // 同
  // init() {
  //  x = 11,
  //  y = 22,
  // }
   var onlyReadValue: Double { x*2 } // 这里省略了get和return
}
```

### 延迟存储属性

1. 使用lazy可以定义一个延迟存储属性，在第一次用到属性的时候才会进行初始化
2. lazy属性必须是var，不能是let(因为let必须在实例的初始化方法完成之前就拥有值)
3. 如果多线程同时访问lazy，那么lazy就无法保证只被初始化一次
4. 当结构体包含一个延迟存储属性时，只有var才能访问延迟存储属性(因为延迟属性初始化时需要改变结构体的内存)

```swift
class Car {
   init() {
      print("Car init!")
   }
   func run() {
      print("Car is running!")
   }
}
class Person {
   lazy var car = Car()
init() {
      print("Person init!")
   }
   func goOut() {
      car.run()
   }
}
let p = Person()
print("-------------")
p.goOut()
 
 
// 打印结果
// Person init!
// --------------
// Car init!
// Car is running!
 
 
// 经典的设置图片的方法
class PhotoView {
   lazy var image: Image = {
      let url = "https://www.baidu.com/xx.png"
      let data = Data(url: url)
      return Image(data: data)
   }() // 一个闭包表达式，并不是计算属性的get方法
}
```

### 属性观察器

1. 可以为非lazy的var存储属性设置属性观察器
2. willSet会传递新值，默认叫newValue
3. didSet会传递旧值，默认叫oldValue
4. 在初始化中设置属性值不会触发willSet和didSet
5. 

## 计算属性

1. set传入的新值默认叫做newValue，也可以自定义

2. 只读计算属性: 只有get，没有set

3. 计算属性只能用var，不能用let

4. 枚举的rawValue本质就是计算属性,只读计算属性

   ```swift
   enum Season: Int {
      case spring = 1,summer,autumn,winter
    
    
      var rawValue: Int {
        switch self {
          case .sprint:
            return 11
          case .summer:
            return 22
          case .autumn:
            return 33
          case .winter:
            return 44
        }
      }
   }
   var s = Season.sumer
   print(s.rawValue) // 22
   ```

## 类型属性

1. 严格来说，属性可以分为
   1. 实例属性，只能通过实例去访问
      1. 存储实例属性: 存储在实例的内存中，每个实例都有一份
      2. 计算实例属性
   2. 类型属性：只能通过类型去访问
      1. 存储类型属性: 整个程序运行过程中，就只有1份内存(类似于全局变量)
      2. 计算类型属性
2. 可以通过static定义类型属性
3. 如果是类，也可以用关键字class定义类型属性

```swift
struct Shape {
   var width: Int = 0
   static var count: Int = 0
}
var s = Shape()
// s.count = 10 // 报错，类型属性只能通过类型去访问
Shape.count = 10
print(Shape.count) // 10
```

### 类型属性细节

1. 不同于**存储实例属性**，你必须给**存储类型属性**设定初始值

   1. 因为类型没有像实例那样的init初始化器来初始化存储属性

2. 存储类型属性

   默认就是lazy，会在第一次使用的时候才初始化

   1. 就算被多个线程同时访问，保证只会初始化一次
   2. **存储类型属性**可以是let(因为他不是实例，实例的初始化方法完成之前要求let拥有值)

3. 枚举类型也可以定义类型属性(存储类型属性、计算类型属性)

4. 单例模式

   ```swift
   // 单例的写法
   class FileManager {
      public static let shared = FileManager()
      private init() { }
      func open() {
     
      }
   }
   FileManager.shared.open()
   ```

### 存储类型属性

全局变量在内存中是一直存在的，这是c语言的特性

```swift
int num1 = 10
int num2 = 11
int num3 = 12
// 对应汇编,3个值挨着在全局区域
0x10001956 movq $0xa, 0xc34f(%rip)  // num1地址值: rip+0xc34f = 0x10000DCB0
0x10001961 movq $0xb, 0xc34c(%rip)  // num2地址值: rip+0xc34c = 0x10000DCB8
0x1000196c movq $0xc, 0xc349(%rip)  // num3地址值: rip+0xc349 = 0x10000DCc0
0x10001977
 
 
修改代码2代：
int num1 = 10
class Car {
   static var count = 1 // 看汇编知道这里调用了swift_once，swift_once里面又调用了dispatch_once，保证了只初始化了一次和线程安全
}
Car.count = 11
int num3 = 12
// 看汇编，3个值挨着在全局区域，所以可知道，static var count从内存角度看，是在全局变量，只是定义在类里，加上了访问限制控制，只能Car.count访问，不能直接count访问
movq $0xa, 0xc7fd(%rip)  // 算出来的值是0x10000DDD0
...
movq $0xb, (%rax)  // 可知，将11给了rax存储的那个值(这个值也是个内存地址)所指向位置，在汇编该句打上断点，控制台输入:register read rax 得rax = 0x10000ddd8
...
movq $0xa, 0xc7fd(%rip)  // 算出来的值是0x10000DDE0
```

# 可选链(Optional Chaining)

可选项的返回值也是可选型

```swift
class Person {
   
    var name: String = ""
    var dog: Dog = Dog()
    var car: Car? = Car()
    func age() -> Int { 18 }
    func eat() { print("Person eat") }
    subscript(index: Int) -> Int { index }
}

var person: Person? = Person()

var age1 = person!.age() // Int
var age2 = person?.age() // Int?
var name = person?.name // String?
var index = person?[6] // Int?
var res = person?.eat() // ()?
```

1. 如果可选项为nil，调用方法、下标、属性失败，结果为nil
2. 如果可选项不为nil，调用方法、下标、属性成功，结果会被包装成可选项 
3.  如果结果本来就是可选项，不会进行再次包装
4. 可选项调用无返回值的方法的结果可以为nil，也可以为可选型空元组()?，比如person?.eat()，当person为nil，则结果为nil，若person不为nil，则返回值为可选型空元组()?。可选项绑定运用的就是这种原理：
5. 可选链可以理解为在可选项后调用属性，下标和方法
6. 多个？可以链接在一起，如果链中任何一个节点为nil，那么整个链就会调用失败

```swift
var num2: Int? = nil
num2? = 10 // 这里判断num2是nil，所以不会进行后面的赋值，num2还是可选nil
```

# 协议

1. 协议可以用来定义方法、属性、下标的声明，协议可以被枚举、结构体、类遵守(多个协议用逗号隔开)
2. 协议中定义方法时不能有默认参数值
3. 默认情况下，协议中定义的内容必须全部都实现
4. 协议中定义属性时必须用var关键字
5. 协议定义get、set，用var存储属性或get、set计算属性去实现
6. 协议定义get，用任何属性都可以实现
7. Optional
   1. 在Oc中有 @optional 关键字，但在 swift 中使用 optional 关键字时，会有警告，提示我们要使用@objc，这很不 swift。而且，被标记为@objc 的协议，只能被 class 实现，不能用于结构体和枚举。
   2. 可通过扩展协议实现Optional，在协议中定义方法，在扩展协议中给出默认实现，而有默认实现的协议方法就是可选方法。
8. 一个协议可以继承其他协议

```swift
protocol Drawable {
   func draw()
   var x: Int { get set}  // 读写属性，并不是说是计算属性,实现可以存储可以计算
   var y: Int { get }  // 只读属性，必须用var，因为如果实现是计算属性，计算里可能返回的值是运行时才能确定的，就不能用let
   subscript(index: Int) -> Int { get set}
}
class Person1：Drawable {
   var x: Int = 0
   let y: Int = 0
   func draw() {
     print("Person draw")
   }
   subscript(index: Int) -> Int {
      set {}
      get { index }
   }  
}
class Person2：Drawable {
   var x: Int {
      set {  }
      get { 0 }
   }
   let y: Int { 0 }
   func draw() {
     print("Person draw")
   }
   subscript(index: Int) -> Int {
      set {}
      get { index }
   }  
}
```

## static、class

1. 为了保证通用，协议中必须用static定义类型方法，类型属性，类型下标
2. 实现方如果允许子类重写，就写成class，不允许就写成static
3. 值类型(枚举、结构体)不支持继承，只有类支持继承

```swift
protocol Drawable {
    
  static func draw()
}
class Person: Drawable {
   
    class func draw() {
      print("Person draw")
    }
}
class Student: Person {
    
    override class func draw() {  // 如果Person是用 static，会报错Cannot override static method
        print("Student draw")
    }
}
struct Circle: Drawable {
    
    static func draw() {  // 只有类能用class,只有类有继承
        print("Circle draw")
    }
}
```

## mutating

1. 只有将协议中的实例方法标记为mutating，才允许结构体、枚举的具体实现修改自身内存，类在实现方法时不用加mutating，枚举、结构体才需要加mutating,(mutating是只有枚举、结构体才能用)

## init、init？、init！

1. 协议中还可以定义初始化器，非final类实现时必须加上required(final类不能被继承，实现可以不用required)
2. 如果从协议实现的初始化器，刚好是重写类父类的指定初始化器，那么这个初始化必须同时加required、override
3. 协议中定义的init？、init！，可以用init、init？、init！去实现
4. 协议中定义的init，可以用init、init！实现

```swift
protocol Livable {
   init()
   init?(age:Int)
   init!(no: Int)
}
class Person：Livable {
   required init() { }
   // required init!() {}
 
 
   required init?(age: Int) { }
   // required init!(age: Int) { }
   // required init(age: Int) { }
 
 
   required init!(no: Int) { }
   // required init?(no: Int) { }
   // required init(no: Int) { }
}
```

## 协议组合

1. 协议组合可以包含一个类类型(最多一个)、多个协议,不能是结构体、枚举；
2. func fn3(obj: Person & Livable & Runable) { } // 接收同时遵守Livable、Runable协议，并且是Person或者其子类的实例
3. typealias RealPerson = Person & Livable & Runable

## CaseIterable

1. 让枚举遵守CaseIterable协议，可以实现遍历枚举值

```swift
class Person: CaseIterable {
    
    typealias AllCases = [Person]
      
    static var allCases: [Person] { get { [Person(), Person()] } }
}


let person = Person.allCases
print(person.count)
for per in person {
   print(per)
}
 
enum Season: CaseIterable {
   case spring, summer, autumn, winter
}
let seasons = Season.allCases
print(seasons.count)
for season in seasons {
   print(season)
}
```

## CustomStringConvertible

1. 遵守CustomStringConvertible、CustomDebugStringConvertible协议，可以自定义实例的打印字符串
2. print调用的是CustomStringConvertible的description
3. debugPrint、控制台的po调用的是CustomDebugStringConvertible协议的debugDescription

```swift
class Person: CustomStringConvertible, CustomDebugStringConvertible {
    
    var description: String { "_____CustomStringConvertible____" }
    var debugDescription: String { "_____CustomDebugStringConvertible____" }
}
 
var person = Person()
 
print(person) // _____CustomStringConvertible____
debugPrint(person)  // _____CustomDebugStringConvertible____ // 同(lldb)po person
```

## Any、AnyObject

1. Swift提供类2种特殊的类型: Any、AnyObject
   1. Any: 可以代表任意类型(枚举、结构体、类，也包括函数类型)
   2. AnyObject: 可以代表任意类类型(在协议后面写上: AnyObject代表只有类能遵守这个协议) （protocol Runable: AnyObject { }）

## is、as？、as！、as

1. is用来判断是否为某种类型，as用来做强制类型转换
2. 对于百分比可以转换的就可以直接用as

```swift
var stu: Any = 10
(stu as? Student)?.study() // 没有调用study
stu = Student()
(stu as? Student)?.study() // 调用study
(stu as! Student).study() // 调用study
 
var data = [Any]()
data.append(Int("123") as Any)
print(data.count)
```

## X.self、X.Type、AnyClass、type(of: x)

1. X.self是一个元类型(metadata)的指针，metadata存放着类型相关信息

2. X.self 属于X.Type类型

3. var p: Person = Person(); var pType: Person.Type = Person.self //看汇编可知pType放着8个字节，内容是p的前面8个字节也就是类型信息

4. AnyClass就是AnyObject.Type

5. var per = Person();var perType = type(of: per); // Person.self 看汇编可知它是将per的前面8个字节里的内容拿出来赋值给了perType，底层并没有分配栈空间(函数调用会分配栈空间)，也没有call 汇编指令

6. 虽然swift说并没有向oc那样所有的类最终继承一个基类，没有父类的类，称为: 基类； 但是通过调用print(class_getSuperclass(Person.self)!) 可以看到虽然Person没有父类，但是打印结果是Swift_SwiftObject(隐藏的基类)

7. class_getInstanceSize：表示对象实际用到的内存大小，也就是存储属性需要的最小值

   class_getSuperclass:获取父类，我们可以看到Person虽然没继承任何类，但它有一个要隐藏的基类Swift._SwiftObject

8. type(of: )获取元类型的指针

```swift
class Person { var age: Int = 0 }
class Student: Person { var no: Int = 0 }
  
var p: Person = Person()
var stu: Student = Student()
var pType: Person.Type = Person.self

var subType: Student.Type = Student.self
pType = Student.self  // 由于Student继承自Person，所以pType = Student.self也成立，好比多态的父类指针指向子类对象，但是不继承自Person的类不可以这么写


var perType = type(of: p) // Person.Self

print(Person.self == perType)  // true
 
print(class_getInstanceSize(Student.self)) // 32,类自带16个字节，存储类型信息和引用计数
print(class_getSuperclass(Student.self)!) // Person
print(class_getSuperclass(Person.self)!) // _TtCs12_SwiftObject
```

## Self

1. Self代表当前类型
2. Self一般用作返回值类型，限定返回值跟方法调用者必须是同一类型(也可以作为参数类型)
3. 有点类似于Oc的instanceType(当前实例的类型)
4. 如果Self用在类中，要求返回时调用的初始化器是required的

```swift
protocol Runable {
   func test() -> Self
}
class Person: Runable {
    
    var age = 1
    class var count: Int {
        get {
            return 2
        }
    }
   required init(){ } // 如果Self用在类中，要求返回时调用的初始化是required的
//   func test() -> Self {
//    Person() as! Self // 会报错，因为如果是子类调用这个方法的话，返回的就不应该是Person实例了
//     type(of: self).init()
//   }
    func test() -> Self {
        
        print(self.age, Self.count)
        return type(of: self).init()
    }
}
 
 
class Student: Person {
    
    class override var count: Int {
        get {
            return 22
        }
    }
    
    required init() {
        super.init()
        self.age = 11
    }
}

var stu = Student()
print(stu.test()) // 11 22 SwiftTest.Student

var per = Person()
print(per.test()) // 1 2 SwiftTest.Person
```

5. 完全相同的几种调用

```swift
class Person {
   static var age = 0
   static func run() { }
}
Person.age = 10
Person.run()
 
 
// 和上面完全相同
Person.self.age = 10
Person.self.run()
 
 
// 下面四句产生的汇编一样
var p1 = Person.init()
var p2 = Person.self.init()
var p3 = Person.self()
var p4 = Person()
```

# 错误处理

## 错误类型

开发过程常见的错误

1. 语法错误
2. 逻辑错误
3. 运行时错误(可能会导致闪退，一般也叫做异常)

## 自定义错误

 Swift中可以通过Error协议自定义运行时的错误信息，一旦抛出异常，它作用域范围内下的所有代码都不会执行

```swift
func test(){
   print("1")
   do {
      print("2")
      print(try divide(20,0))
      print("3")
   } catch let SomeError.illegalArg(msg) {
      print("参数异常:",mes)
   } catch let SomeError.outOfBounds(size,index) {
      print("下标越界:","size=\(size)","index=\(index)")
   } catch SomeError.outOfMemory {
      print("内存溢出")
   } catch {
      print("其他错误")
   }
  
   // 或者
   //do {
   //  
   //} catch let error as SomeError {
   //  
   //}
  // 又或者
  // do {
  //  
  // } catch is SomeError {
  //  
  // }
   print("4")
}
test()
// 1
// 2
// 参数异常: 0不能作为除数
// 4
 
 
func test1() throws {
   print("1")
   print(try divide(20,0))
   print("2")
}
try test1()
```

## 处理Error

处理Error的2种方式

1. 通过do-catch捕捉Error, do可以不跟do-catch一起用，可以单独使用,do{ }  // 定义一个局部作用域 跟 OC的一个{ }一样
2. 不捕捉Error，在当前函数增加throws声明,Error将自动抛给上层函数
3. 如果最顶层函数(main函数)依然没有捕捉Error，那么程序将终止

## try?、try!

```swift
func test(){
   print("1")
   var result1 = try? divide(20,10) // Optional(2), Int?
   var result2 = try? divide(20,0) // nil
   var result3 = try? divide(20,10) // 2, Int
   print("2")
 
 
   // a和b等价
   var a = try? divide(20, 0)
   var b: Int?
   do {
      b = try divide(20, 0)
   } catch { }
}
test()
```

## rethrows

rethrows表明: 函数本身不会抛出错误，但调用闭包参数抛出异常，那么它会将错误向上抛

```swift
// 接收一个会抛出异常的方法，
func exec(_ fn: (Int, Int) throws -> Int, _ num1: Int, _ num2: Int) rethrows {
   print(try fn(num1,num2))
}
try exec(divide, 20, 0)
```

##defer

1. defer语句: 用来定义以任何方式(抛错误、return等)离开代码块前必须要执行的代码
2. defer语句将延迟至当前作用域结束之前执行
3. defer语句的执行顺序与定义顺序相反(以大括号从下往上执行)

```swift
class Person {
   static func run() {
    
    defer {
        print(1)
        print(2)
        print(3)
    }
    defer {
        print(11)
        print(22)
        print(33)
    }
     print("hhhhhhh")
   }
}
Person.run()
// 打印
//hhhhhhh
//11
//22
//33
//1
//2
//3
```

##assert(断言)

1. 很多编程语言都有断言机制: 不符合指定条件就抛出运行时错误，常用于调试(Debug)阶段的条件判断
2. 默认情况下，Swift的断言只会在Debug模式下生效，Release模式下会忽略
3. 但是不能通过do-catch捕捉
4. 修改Swift Flages断言的默认行为
   1. 在Build Setting下搜索other Swift，
   2. 在Other Swift Flages下的Debug下输入-assert-config Release : 强制关闭断言
   3. 在Other Swift Flages下的Release下输入-assert-configDebug : 强制开启断言

##fatalError

1. 如果遇到严重问题，希望结束程序运行时，可以直接使用fatalError函数抛出错误(这是无法通过do-catch捕捉的错误)
2. 使用了fatalError函数，就不需要再写return
3. 在某些不得不实现，但不希望别人调用的方法，可以考虑内部使用fatalError函数
   1. 比如说子类继承父类的required init方法时，但是不希望别人调用，可以在init里面写fatalError函数

# 泛型(Generic)

1. 泛型可以将类型参数化，提高代码复用率，减少代码量
2. 泛型函数赋值给变量
3. 泛型类型

```swift
 var n1 = 10
 var n2 = 20
// 泛型可以将类型参数化
 func swapValues<T>(_ a: inout T, _ b: inout T) {
    (a,b) = (b,a)
 }
 swapValues(&n1,&n2) // 从汇编可以看出，虽然泛型的类型不同，但是可以看出两个函数的地址都是同一个，调用的同一个函数，可以区分不同类型进行计算，主要是靠metadata也就是元类型来实现的
  
 // 泛型函数赋值给变量
 func test<T1,T2>(_ t1:T1,_ t2:T2) { }
 var fn: (Int,Double) -> () = test
  
  
 // 泛型类型
 class Stack<E> {
    var elements = [E]()
    func push(_ element: E) { elements.append(element) }
    func pop() -> E { elements.removeLast() }
 }
 // 继承一个泛型类型
 class SubStack<E>: Stack<E> { }
  
  
 enum Score<T> {
    case point(T)
    case grade(String)
 }
 let score3 = Score<Int>.grade("A") // 即使这里没有用point，也需要指明这个T是什么类型，不然无法分配合适的内存
```

## 关联类型

1. 关联类型的作用: 给协议中用到的类型定义一个占位名称(associatedtype)
2. 协议中可以拥有多个关联类型

```swift
protocol Stackable {
   associatedtype Element // 关联类型
   mutating func push(_ element: Element)
   mutating func pop() -> Element
   func top() -> Element
   func size() -> Int
 }
  
  
 // 实现
 class StringStack: Stackable {
     // 给关联类型设定真实类型
     // typealias Element = String  // 可以省略，系统会自动判断出来,这里的String也可以改成指定为泛型
     var elements = [String]()
    func push(_ element: String) {   }
     func pop() -> String { "" }
     func top() -> String { elements.last! }
     func size() -> Int { elements.count }
 }

// 实现
class TStack<T>: Stackable {
    
    typealias Element = T 
  
    var elements = [T]()
    func push(_ element: T) {   }
    func pop() -> T { elements.first!}
    func top() -> T { elements.last! }
    func size() -> Int { elements.count }
}
```

## 类型约束

```swift
// 这个泛型遵守Runable协议并且是Person的子类
func swapValue<T: Person & Runable>(_ a: inout T, _ b: inout T) { }

// 多个泛型结合where的复杂约束：
// S1和S2两个泛型都遵守Stackable协议，并且S1和S2的Element属于同一类型，S1的Element遵守Hashable协议
func equal<S1: Stackable, S2: Stackable>(_ s1: S1, _ s2: S2) -> Bool where S1.Element == S2.Element, S1.Element : Hashable{
    return true
}

```

### 类型约束注意点

1. 如果协议中有关联类型，那么就不能用做返回值或者参数，因为无法在编译时确定关联类型的值

```swift
 protocol Runable {
   associatedtype Speed // 关联类型
    var speed: Speed { get }
 }
  
  
 // 实现
 class Person: Runable  {
      
    var speed: Double { 1.1 }
 }

// 实现
class Car: Runable {
    
    var speed: Double { 100 }
}

// 报错-无法在编译时确定关联类型的值
func get(_ type: Int) -> Runable { // 如果协议中有关联类型，那么就不能用做返回值或者参数，

    if type == 0 {
        return Person()
    }
    return Car()
}

// 报错-无法在编译时确定关联类型的值
func get(_ type: Runable)  {
    
}

// 方案1修改,以下不会报错
func get<T: Runnable>(_ type: Int) -> T {
    if type == 0 {
        return Person() as! T
        
    }
    return Car() as! T
    
}
var r2: Car = get(1) // 这里的Car会报告编译器Speed是什么类型(Int)，调用调用get的时候，告诉get遵守Runnable的T这里是Car

// 方案2修改,使用不透明类型some, 但是他限制只能返回一种类型
func get(_ type: Int) -> some Runnable { // 由于限制只返回一种类型，如果有Person又有Car就会报错return Car()

    return Car()  
}
```

### some的使用

1. 不透明类型some，但是他限制只能返回一种类型
2. some除了用在返回值类型上，一般还可以用在属性类型上

```swift
protocol Runnable {
    
    associatedtype Speed
}

class Dog: Runnable {
    
    typealias Speed = Double
}

class Person {
    
    var pet: some Runnable {
        return Dog()
    }
}
```

# 汇编分析String、Array底层

## String

1. 一个String变量占用多少内存？
2. 一个长String和一个短String，底层存储有什么不同？
3. 如果对String进行拼接操作，String变量的存储会发送什么变化？

![内存地址](/Swift入门到精通笔记/内存地址.png)

```swift
var str1 = "0123456789"

print(Mems.memStr(ofVal: &str1))

// 通过汇编知道用了16个字节存储这个str1，通过打印地址看该地址的内容，知道Str1地址下存储内容为：// 0x3736353433323130 0xea0000000000003938

// 可知存储的是ASCII码上数字所对应的值，0xe是一种格式，表示是直接将内容存储在地址下，a表示实际值存储用了多长// 此种技术类似于Oc的targer pointer
// 看汇编Swift.String.init 方法里有一个指令: cmpq $0xf, %rsi 意思是拿15和rsi比较长度，这个rsi就是字符串长度，<=15的话，内容会直接存在16个字节里头，

// >15的话比如str是"0123456789ABCDEF"，看汇编可以看见str内容为0xd000000000000010 0x80000001000056e0 ；由汇编可知 字符串的真实地址+0x7FFFFFFFFFFFFFE0 = 0x80000001000056e0(后面8个字节) 所以字符串真实内容存放位置是0x100005700，通过x 0x100005700查看内容可以看到0x100005700: 30 31 32 33 34 35 36 37 38 39 41 42 43 44 45 46 0123456789ABCDEF；// 另外从0xd000000000000010 0x80000001000056e0 还可以得知第一个8个字节，的最后1个字节用来存储字符串长度
```

4. 通过看MachOView里的Section64(_TEXT, _cstring)的pFile是00005700的value是0123456789ABCDEF，是字符串的真实地址，所以字符串放在Section64(_TEXT, _cstring)这个区(常量区)
5. 虽然说通过汇编，看到的是0x938e(%rip)即rip+一个很大的值, 这是因为字符串的这个常量区的地址和全局区Section64(_DATA _data)的地址很近(可以通过MachOView看出来)
6. 不管这个字符串多长，真实地址都是在常量区，只是少于15的时候，Str存储的内容就直接是常量值，便于访问，大于15的，由于16个字节放不下，所以str后面8个字节存储的是字符串的真实地址+0x7FFFFFFFFFFFFFE0，前面8个字节的最后一个字节用来存储字符串长度，第一个字节可以认为是代表Str存储的是地址的一种标示位

![String底层1](/Swift入门到精通笔记/String底层1.jpeg)

![String底层2](/Swift入门到精通笔记/String底层2.jpeg)

###从编码到启动APP

1. 从图可以看出我们平常编写的代码常量区、堆区、代码区等内存都在Mach-O位置
2. 所以也可以通过看mach-O文件的内容来判断写的某个内容在哪个位置
3. 查看mach-O文件工具---- machOView
4. 用MachOView打开会看到Section64(_TEXT, _text)这里面就是写的代码段，写的代码都在这里
5. 如果在MachOView里的Section64(_TEXT, _cstring)看到的第一句是00005700(第一行的pFile)，我们在代码中或汇编中看到的指令会是0x100005700
6. 通过看MachOView里的Section64(_TEXT, _cstring)的pFile是00005700的value是0123456789ABCDEF，是字符串的真实地址，所以字符串放在Section64(_TEXT, _cstring)这个区(常量区)
7. Section64(_TEXT, ..)都可以认为是代码段,再细分的话Section64(_TEXT, _cstring)就是字符串常量区

![编译链接内存](/Swift入门到精通笔记/编译链接内存.png)

```swift
var str0 = "0123"
print(Mems.memStr(ofVal: &str0)) // 0x0000000033323130 0xe400000000000000
str0.append("4")
print(Mems.memStr(ofVal: &str0))  // 0x0000004733323130 0xe500000000000000


// 由此可见，只要还放得下，还是会直接存储内容
var str1 = "0123456789ABCDEF"
print(Mems.memStr(ofVal: &str1)) // 0xd000000000000010 0x80000001000056c0
// 真实字符串地址: 0x80000001000056c0-0x7FFFFFFFFFFFFFE0 等价于8改为0 0x00000001000056c0+0x20 = 0x1000056E0
str1.append("G") // 通过看汇编可以看到当调用.append()方法时又调用了一次String的init，参数是G，这是在初始化G，会把G变成一个字符串，
print(Mems.memStr(ofVal: &str1))  // 0xf000000000000011 0x0000000100702830
 

// 真实字符串地址: 0x0000000100702830+0x20 = 0x100702850 通过x 0x100702850 可以读取里面的内容正是字符串内容， 对于一个8位8字节，如果前面4个都是0，说明他在堆空间;
// 证明方式: 通过alloc一个对象，找到malloc方法，在该汇编处打上断点，然后看str1.append有没有也调用到这个断点，到这个断点的时候控制台输入bt可以看到调用栈就有swift_slowAlloc和malloc等方法调用栈还有一个Swift._StringGuts.append(....，所以说明str1.append底层会调用malloc，调用malloc的在堆空间，malloc返回的是堆空间地址值，返回值是rax表示，通过控制台输入register read rax得到的值刚好就是通过0x0000000100702830，这就是堆空间地址值，这个地址值+0x20(跳过32个字节)就是字符串真实地址
```

```swift
// 字符串长度 <= 0xF,字符串内容直接存放在str1变量的内存中
var str1 = "0123456789"
 
// 字符串长度 > 0xF, 字符串内容存放在_TEXT, _cstring中(常量区)
// 字符串的地址值信息存放在str2变量的后8个字节中
var str2 = "0123456789ABCDEFGHIJ"
 
// 由于字符串长度 <= 0xF, 所以字符串内容依然存放在str1变量的内存中
str1.append("ABCDE")
// 开辟堆空间
str1.append("F")
// 开辟堆空间
str2.append("F")
```

###dyld_stub_binder

1. stub符号：stub_binder：符号绑定
2. 在machOView文件的Load Commands里就有所有载入内存的所有依赖的动态库
3. init函数就是动态库中的方法，但是动态库的方法是在程序运行过程中才载入的，所以方法地址是运行时才能确定的，但是编译的时候就需要确定方法地址，所以汇编看到的该地址是一个占位地址，并不是真实方法地址，改占位地址方法有一个jmpq，这个指令会跳转到一个数据段地址，这个地址会指引他去dyld_stub_binder里
4. 这个占位地址方法里面会调用到dyld_stub_binder库里，这里在做符号绑定，将真正地址值(一个超大的地址值0x7f)更新到数据段地址里存储的内容

## Array

###关于Array的思考

1. 1个Array变量占用多少内存？
2. 数组中的数据存放在哪里？
3. 证明方法和String一样，
4. 结论:
   1. 内容放在堆空间
   2. ![Array底层](/Swift入门到精通笔记/Array底层.png)
   3. 这个容量并不是一个一个增加的，是以8为倍数增加

     ## 总结

### 总结

1. 虽然说Array和String是值类型，有值类型的特性行为，比如修改内容需要加mutating；
2. 但是从汇编看，Array和String是分配了堆空间的，底层是引用类型

# 可选项、高级运算符、扩展

## 可选项的本质

1. 可选项的本质是enum类型
2. Wrapped叫关联值

```swift
public enum Optional<Wrapped> : ExpressibleByNilLiteral {
    case none
    case some(Wrapped)
    public init(_ some: Wrapped)
}
 

var age1: Optional<Int> = .some(10)
age1 = .none
age2 = .some(20)
 
 
switch age {
   // v是不带？的类型，如果这里是v?,不是v，那么这个v是可选项，和可选项绑定不同
   case let v?:
     print("some",v)
   case nil:
     print("none")
}
 
 
var age_: Int? = 10
var age: Int?? = age_
// 即:
var age_ = Optional.some(Optional.some(10))
var age: Optional<Optional> = .some(.some(10))
```

##高级运算符

###溢出运算符

1. Swift的算数运算符出现溢出时会抛出运行时错误
2. Swift有溢出运算符(&+、&-、&*),用来支持溢出运算

```swift
var v1 = UInt8.max  // UInt8 0---255
var v2 = v1 &+ 1  // v2 = 0
```

###运算符重载

1. 类、结构体、枚举可以为现有的运算符提供自定义的实现，这个操作叫做: 运算符重载

```swift
struct Point {
   static func +(p1: Point, p2: Point) -> Point {
      Point(x: p1.x + p2.x, y: p1.y + p2.y)
   }
   static prefix func -(p: Point) -> Point {
      Point(x: - p.x, y: - p.y)
   }
   static func +=(p1: inout Point, p2: Point) {
      p1 = p1 + p2
   }
}
 
var p1 = Point(x: 10, y: 20)
var p2 = Point(x: 11, y: 22)
let p3 = -p2   // 前缀运算符用法
p1 += p2
```

###Equatable

1. 遵守Equatable协议，实现==， !=就自动有效能用
2. Swift为以下类型提供默认的Equatable实现
   1. 没有关联类型的枚举(协议都可以不用写)
   2. 只拥有遵守Equatable协议关联类型的枚举(需要遵守协议)
   3. 只拥有遵守Equatable协议存储属性的结构体
3. 引用类型比较存储的地址值是否相等(是否引用着同一个对象)，使用恒等运算符 ===、!==

```swift
enum Answer: Equatable {
   case wrong(Int)
   case right
}
var s1 = Answer.wrong(10)
var s2 = Answer.wrong(10)
print(s1 == s2)  // true
```

###Comparable

1. 要想比较2个实例的大小，一般做法是:
   1. 遵守Comparable协议
   2. 重载相应的运算符

###自定义运算符

1. 可以自定义新的运算符: 在全局作用域使用operator进行声明
2. [Apple文档参考: ](https://developer.apple.com/documentation/swift/swift_standard_library/operator_declarations)

```swift
prefix operator +++   // 前缀运算符
postfix operator ---  // 后缀运算符
infix operator +-: PlusMinusPrecedence  // 中缀运算符： 优先级组
precedencegroup PlusMinusPrecedence {
 
    associativity: none  // 结合性(left\right\none) ， 即用多个这个运算符时候，从左边还是右边开始算，如果是none，说明不允许多个这个运算符比如 a1+a2+a3就是left结合性
    higherThan: AdditionPrecedence  // 比谁的优先级低
    lowerThan: MultiplicationPrecedence  // 比谁的优先级高
    assignment: true  // 代表在可选链操作中拥有跟赋值运算符一样的优先级
}
 
prefix func +++ (_ i: inout Int) {
    i += 2
}
var age = 10
+++age
print(age)
```

###扩展

1. Swift的扩展，有点类似于Oc中的分类
2. 扩展可以为枚举、结构体、类、协议添加新功能
   1. 可以添加方法、计算属性、下标、(便捷)初始化器、嵌套类型、协议等等
3. 扩展不能办到的事情
   1. 不能覆盖原有的功能
   2. 不能添加存储属性，不能向已有的属性添加属性观察器
   3. 不能添加父类
   4. 不能添加指定初始化器，不能添加反初始化器
   5. 类遵守协议实现的required初始化器，不能写在扩展中
   6. ...
4. 扩展可以给协议提供默认实现，间接实现可选协议的效果
5. 扩展可以给协议扩充 协议中从未声明过的方法

```swift
extension Array {
 
 subscript(nullable idx: Int) -> Element? {
 if (startIndex ..< endIndex).contains(idx) {
 return self[idx]
 }
 return nil
 }
}
 
 
extension Int {
 func repeates(task: () -> Void) {
 for _ in 0..<self { task() }
 }
}
 
 
2.repeates {
 print("1")
}
```

###扩展带泛型的

```swift
class Stack<E> {
 var elements = [E]()
 func push(_ element: E) {
 elements.append(element)
 }
}
 
// 扩展中依然可以使用原类型中的泛型类型
extension Stack {
 func top() -> E {
 elements.last!
 }
}
 
// 符合条件才扩展
extension Stack: Equatable where E: Equatable {
 static func == (left: Stack, right: Stack) -> Bool {
 left.elements == right.elements
 }
}
```

#访问控制、内存管理

## 访问控制

1. 在访问权限控制这块，Swift提供了5个不同的访问级别(以下是从高到低排列，实体指被访问级别修饰的内容)
2. open: 允许在定义实体的模块、其他模块中访问，允许他们模块进行继承、重写(open只能用在类、类成员上)
3. public: 允许在定义实体的模块、其他模块中访问，不允许他们模块进行继承、重写
4. internal: 只允许在定义实体的模块中访问，不允许在他们模块中访问
5. fileprivate:只允许在定义实体的源文件中访问
6. private: 只允许在定义实体的封闭声明中访问
7. 绝大部分实体默认都是internal级别

### 访问级别的使用准则

1. 一个实体不可以被更低访问级别的实体定义

### 元组类型的访问级别

1.  元组类型的访问级别是所有成员类型最低的那个

### 泛型类型的访问级别

1.  泛型类型的访问级别是 类型的访问级别 以及 所有泛型类型参数的访问级别 中最低的哪个

```swift
internal class Car { }
fileprivate class Dog { }
public class Person<T1, T2> { }
 
// Person<Car, Dog> 的访问级别是fileprivate
fileprivate var p = Person<Car, Dog>()
```

### 成员、嵌套类型的访问级别

1. 类型的访问级别会影响成员(属性、方法、初始化器、下标)、嵌套类型的默认访问级别
2. 一般情况下，类型为private或fileprivate，那么成员\嵌套类型默认也是private或fileprivate(和类型一样作用域)
3. 一般情况下，类型为internal或public，那么成员\嵌套类型默认是internal

### 例子

1. 直接在全局作用域下定义的private等价于fileprivate
2. 例子: 

```swift
// 会报错
// 因为private表示只允许在定义实体的封闭声明中访问，那么它的作用域是test大括号里，这种情况下，Car下的属性会默认是private权限级别但是是和Car作用域相同，即test大括号里
// fileprivate:只允许在定义实体的源文件中访问， 这整个.swift都可以访问
// 又由于一个实体不可以被更低访问级别的实体定义，所以Car>=Dog才行，所以这里报错
class test {
     
    private class Car { }
    fileprivate class Dog : Car { }
}
 
// 不会报错
// Car作用域在整个文件,这种情况下，Car下的属性会默认是filePrivate权限级别，Dog也是整个文件
private class Car { }
fileprivate class Dog : Car { }
```

### getter、setter

1. getter、setter默认自动接收它们所属环境的访问级别
2. 可以给setter单独设置一个比getter更低的访问级别，用以限制写的权限

```swift
class Person {
    // 设置setter的级别是private，
    // getter的级别默认自动接收所属环境的访问级别也就是和Person相同
    // 所以效果是外界可以访问age，但是更改不了age
    private(set) var age = 0
}
```

###初始化器

1. 如果一个public类想在另一个模块调用编译生成的默认无参初始化器，必须显示提供public的无参初始化器
2. required初始化器必须跟它所属类拥有相同的访问级别
3. 如果结构体有private\fileprivate的存储实例属性，那么它的成员初始化器也是private\fileprivate（成员初始化器就是带初始化成员的）,否则默认就是internal

###枚举

1. 不能给enum的每个case单独设置访问级别
2. 每个case自动接收enum的访问级别
   1. public enum定义的case也是public

###协议

1. 协议中定义的要求自动接收协议的访问级别，不能单独设置访问级别
2. public协议定义的要求也是public
3. 协议实现的访问级别必须 >= 类型的访问级别，或者 >= 协议的访问级别

###扩展

1. 如果有显示设置扩展的访问级别，扩展添加的成员自动接收扩展的访问级别
2. 如果没有显示设置扩展的访问级别，扩展添加的成员的默认访问级别，跟直接在类型中定义的成员一样
3. 可以单独给扩展添加的成员设置访问级别
4. 不能给用于遵守协议的扩展显示设置扩展的访问级别
5. 在同一文件中的扩展，可以写成类似多个部分的类型声明
   1. 在原本的声明中声明一个私有成员，可以在同一文件的扩展中访问它
   2. 在扩展中声明一个私有成员，可以在同一文件的其他扩展中、原本声明中访问它

###将方法赋值给var\let

1. 如果有显示设置扩展的访问级别，扩展添加的成员自动接收扩展的访问级别
2. 如果没有显示设置扩展的访问级别，扩展添加的成员的默认访问级别，跟直接在类型中定义的成员一样

```swift
struct Person {
   var age: Int
   func run(_ v: Int) { print("djlsjflj") }
   static eat(_ v: Int) { print("eat") }
}
// fn1 的类型是 (Person) -> ((Int) ->()) // 接收一个Person实例作为参数，返回一个函数
var fn1 = Person.run
var fn2 = fn1(Person(age: 10))
fn2(20)
 
 
// 类型方法不需要传实例
var fn3 = Person.eat
fn3(20)
```

## 内存管理

1.  跟OC一样，Swift也是采取基于引用计数的ARC内存管理方案(针对堆空间)
2. Swift的ARC有3种引用
   1. 强引用: 默认情况下，引用都是强引用
   2. 弱引用: 通过weak定义弱引用
      1. 必须是可选类型的var，因为实例销毁后，ARC会自动将弱引用设置为nil
      2. ARC自动给弱引用设置nil时，不会触发属性观察器
   3. 无主引用: 通过unowned定义无主引用
      1. 不会产生强引用，实例销毁时仍然存储着实例的内存地址(类似于OC中的unsage_unretained)
      2. 试图在实例销毁后访问无主引用，会产生运行时错误(野指针)

###weak、unowned的使用限制

1.  weak、unowned只能用在类实例上面

###Autorelesepool

1.  自己创建内存释放池，缓解内存压力

```swift
autoreleasepool {
   let p = MJPerson(age: 20, name: "jack")
   p.run()
}
```

### 循环引用

1.  weak、unowned都能解决循环引用的问题，unowned要比weak少一些性能消耗
   1. 在生命周期中可能会变为nil的使用weak
   2. 初始化赋值后再也不会变成nil的使用unowned

###闭包的循环引用

1.  闭包表达式默认会对用到的外层对象产生额外的强引用(对外层对象进行了retain操作)

```swift
class Person {
     
    var fn: (()->())?
    func run() {
        print("run")
    }
    deinit {
        print("deinit")
    }
}
func test() {
    let p = Person()
    p.fn = {
        [weak p] in
        p?.run()
    }
}
test()
```

2. 如果想在定义闭包属性的同时引用self，这个闭包必须是lazy(因为在实例初始化完毕之后才能引用self)
3. 如果lazy属性是闭包调用的结果，那么不用考虑循环引用的问题(因为闭包调用后，闭包的生命周期就结束了)

###@escaping

1. 非逃逸闭包、逃逸闭包，一般都是当作参数传递给函数
2. 非逃逸闭包: 闭包调用发生在函数结束前，闭包调用在函数作用域内
3. 逃逸闭包: 闭包有可能在函数结束后调用，闭包调用逃离了函数的作用域，需要通过@escaping声明
4. 逃逸闭包不可以捕获inout参数

```swift
typealias Fn = () -> ()

func test1(_ fn: Fn) {
    fn()
}

var gFn: Fn?
func test2(_ fn: @escaping Fn) {
    
    gFn = fn
}

func test(value: inout Int) {

    test1 {
        value += 1
    }
    test2 { // 会报错
        value += 1
    }
}


do {
    var a = 10
    test2{
        a += 1
    }
//    test(value: &a)  // 当这个大括号执行完，a已经销毁了，这时候gFn保存的地址值已经销毁，
}

if gFn != nil { // number 作用域只在这个大阔号里

    gFn!()
    print("1000hhhhh")
}
```

### 内存访问冲突(强制独占性原则)

为了实现 [内存安全](https://docs.swift.org/swift-book/LanguageGuide/MemorySafety.html)，Swift 需要对变量进行独占访问时才能修改该变量。本质上来说，当一个变量作为 `inout` 参数或者 `mutating` 方法中的 `self` 被修改时，不能通过不同的名称被访问的。

[所有权宣言 - Swift 官方文章 Ownership Manifesto 译文评注版](https://onevcat.com/2017/02/ownership/)

1. 内存访问冲突会在两个访问满足下列条件时发生
   1. 至少一个是写入操作
   2. 他们访问的是同一块内存
   3. 他们的访问时间重叠(比如在同一个函数内)
2. 如果下面的条件可以满足，就说明重叠访问结构体的属性是安全的
   1. 你只访问实例存储属性，不是计算属性或者类属性
   2. 结构体是局部变量而非全局变量
   3. 结构体要么没有被闭包捕获要么只被非逃逸闭包捕获

```swift
// 不存在内存访问冲突
func plus(_ num: inout Int) -> Int {
    num + 1
}
var number = 1
number = plus(&number)  // 将number地址传入，返回值之后赋值给自己


// 存在内存访问冲突，运行直接崩溃
// Simultaneous accesses to 0x1000071f8, but modification requires exclusive access.
// Previous access (a modification) started at SwiftTest`main + 171 (0x10000103b).
// Current access (a read) started at:
//var step = 1
//func increment(_ num: inout Int) { num += step } // num就是传进来的step，这句等于step += step
//increment(&step)


// 没问题
var a = 1
a += a
print(a)
```

# 指针

1. Swift中也有专门的指针类型，这些都被定性为"Unsafe"，常见的有

   1. UnsafePointer<Pointee>类似于const Pointee*   // Pointee表示泛型
   2. UnsafeMutablePointer<Pointee>类似于Pointee*   // 可以修改指针指向的值
   3. UnsafeRawPointer 类似于 const void*   // 普通指针，指向的是什么不确定
   4. UnsafeMutableRawPointer 类似于 void*

2. 带泛型的，通过ptr.pointee访问或修改指针指向的值，不带泛型的通过

   ptr.load(as: Int.**self**)获取值，通过ptr.storeBytes(of: 30, as: Int.**self**)存储值，Int.self根据需要修改

3. 运用:

4.  

   ```swift
   var arr = NSArray(objects: 11,22,33,44)
   arr.enumerateObjects { (element, idx, stop) in
      print(idx,element)
    if idx == 2 {
        stop.pointee = true
      }
   }    
   // 更好的遍历方式
   for (idx, element) in arr.enumerated() {
      print(idx,element)
   if idx == 2 {
    break
    }
    }
    
    
   // 创建一个pointer
   var age = 10
   var ptr = withUnsafePointer(to: &age) { &0 }
   // 此时ptr会是一个UnsafePointer<Int>类型
   ```

5. 获得某个变量的指针

6.  

   ```swift
   var age = 10
   var ptr = withUnsafePointer(to: &age) { &0 }
   // 此时ptr会是一个UnsafePointer<Int>类型var ptr1 = withUnsafePointer(to: &age) { UnsafeRawPointer{&0} }
   // 此时ptr1会是一个UnsafeRawPointer类型 // p是指向age的指针， Result是返回给外界的泛型, 返回值是闭包里的执行结果
   // withUnsafePointer(to: &age) { (p) -> Result in
   // <#code#>
   // }
    var ptr = withUnsafePointer(to: &age) { (p) -> Int in
    20
    }
    // 此时ptr就是20
   ```

7. 获取指向堆空间实例的指针

8.  

   ```swift
   class Person {
     
    var age: Int
    init(age: Int) {
    self.age = age
    }
   }
    
   var person = Person(age: 21)
   var ptr1 = withUnsafePointer(to: &person) { UnsafeRawPointer($0) } // person变量地址值
   var address = ptr1.load(as: UInt.self) // 获取person变量所指向的值==堆空间地址值
   var allocPer = UnsafeMutableRawPointer(bitPattern: address) // 根据地址值得到一个指针
    
    
   // 同
   var allocPer2 = unsafeBitCast(person, to: UnsafeRawPointer.self)  //  allocPer和allocPer2相同，都是指向堆空间的指针
     
   print(allocPer) // Optional(0x0000000100732750)
   print(ptr1) // 0x0000000100008398 ptr1指向person变量的地址
    
    
   print(Mems.ptr(ofVal: &person)) // 0x0000000100008398 person变量的地址
   print(Mems.ptr(ofRef: person)) // 0x0000000100732750 堆空间Person对象的地址值
   ```

9.  创建一个指针

10.  

    ```swift
    // 方式一:
    var ptr = malloc(16)  // 返回值 UnsafeMutableRawPointer!
    ptr?.storeBytes(of: 10, as: Int.self)  // 前8个字节赋值10
    ptr?.storeBytes(of: 20, toByteOffset: 8, as: Int.self) // 后面8个字节赋值20
     
     
    ptr?.load(as: Int.self) // 取出前8个
    ptr?.load(fromByteOffset: 8, as: Int.self) // 取出后8个
     
     
    // 销毁
    free(ptr)
     
     
    // 方式二:
    var ptr1 = UnsafeMutableRawPointer.allocate(byteCount: 16, alignment: 1)
    // ptr1.advanced(by: 8)  // 指针往后移动8个字节，返回一个新地址
    ptr1.storeBytes(of: 11, as: Int.self)
    ptr1.advanced(by: 8).storeBytes(of: 22, as: Int.self)
     
     
    ptr?.load(as: Int.self) // 取出前8个
    ptr?.advanced(by: 8).load(as: Int.self) // 取出后8个
     
     
    // 销毁
    ptr1.deallocate()
     
     
    var ptr2 = UnsafeMutablePointer<Int>.allocate(capacity: 3) // 相当于24个字节
    ptr2.initialize(to: 10) // 如果是泛型的话建议用initialize来初始化，这里初始化了前8个字节为10
    //ptr2.successor()  // 后记，指向下一个Int
    ptr2.successor().initialize(to: 20)
    ptr2.successor().successor().initialize(to: 20)
     
     
    print(ptr2.pointee) // 11
    print((ptr2+1).pointee)  // 22
    print(ptr2[2])  // 33
    print(ptr2.successor().successor().pointee)  // 33
     
     
    // 销毁
    ptr2.deinitialize(count: 3) // 一定要写，不然会内存泄漏
    ptr2.deallocate()
    ```

11.  指针之间的转换

    1. unsafeBitCast是忽略数据类型的强制转换，不会因为数据类型的变化而改变原来的内存数据
    2. 类似于C++中的reinterpret_cast

12.  

    ```swift
    var ptr = UnsafeMutableRawPointer.allocate(byteCount: 16, alignment: 1)
    defer {
      ptr.deallocate()
    }
     
     
    //表示假想它为Int类型，ptr1是UnsafeMutableRawPointer<Int>类型,
    var ptr1 = ptr.assumingMemoryBound(to: Int.self)
    (ptr+8).assumingMemoryBound(to: Double.self).pointee = 22 // 这里的+8是加上8个字节,和ptr1不同
    // 因为ptr1明确了类型，它+1就是只加1个Int字节,以类型为单位，而ptr不明确类型，它是以字节为单位
     
     
    // 表示强制转换, ptr2是UnsafeMutableRawPointer<Int>类型,
    var ptr2 = unsafeBitCast(ptr, to: UnsafeMutablePointer<Int>.self)
    ```

# 字面量(Literal)

1. 常见的字面量默认类型

   1. **public** **typealias** IntegerLiteralType = Int
   2. **public** **typealias** FloatLiteralType = Double
   3. **public** **typealias** BooleanLiteralType = Bool
   4. **public** **typealias** StringLiteralType = String

2. 可以通过typealias修改字面量的默认类型

3. 1. **typealias** IntegerLiteralType = Double
   2. **typealias** FloatLiteralType = Int

4. Swift自带的绝大部分类型，都支持直接通过字面量进行初始化

5. Swift自带类型之所以能够通过字面量初始化，是因为它们遵守了对应的协议

   1. Bool: ExpressibleByBooleanLiteral
   2. Int、Float、Dictionary、String、Array、Set都有遵守对应协议

6. 字面量协议应用

   1. 有点类似c++的转换构造函数

```swift
extension Int: ExpressibleByBooleanLiteral {
 
    public typealias BooleanLiteralType = Bool
 
    public init(booleanLiteral value: Self.BooleanLiteralType) {
 
        self = value ? 1 : 0
    }
}
 
var  num: Int = true
print(num)
```

# 模式

1. 模式是用于匹配的规则，比如switch的case、捕捉错误的catch、if\guard\while\for语句的条件等
2. 理解模式有利于写出更精简的代码

## 通配符模式(Wildcard Pattern)

1. _ 匹配任何值
2. _?匹配非nil值

## 标识符模式(Identifier Pattern)

1. 给对应的变量、常量名赋值 (变量、常量名就是标识符)

## 元祖模式(Tuple Pattern)

1. (0,1)

## 枚举Case模式(Enumeration Case Pattern)

1. if case语句等价于只有1个case的switch语句
2. 判断age是否大于0小于10、判断ages数组中是否有nil值

```swift
var age = 2
// 原来方法
 if age > 0 && age <= 9 {
     print("（0~9】")
 }
 
 
// 更优雅的办法
if case 0...9 = age { 
   print("（0~9】") 
}   
// 判断ages数组中是否有nil值
let ages:[Int?] = [2,3,nil,5]
        for case nil in ages {
            print("有nil值")
            break
        }  
// 循环
let points = [(1,0),(2,0),(3,0)]
for (x,y) in points {
   print(x,y)
}
for (x,0) in points {  //会报错
}
for case let (x,0) in points {
   print(x)
}
```

## 可选模式(Optional Pattern)

1. if case let x? = age { print(x) }  // 判断age为非nil，并将age进行解包，将解包后的值打印
2. 同:  if case .some(let x) = age { print(x) } 

## 类型转换模式(Type-Casting Pattern)

```swift
let num1: Any = 6
        switch num1 {
        case is Int:
            print("is Int",num1)
        case let n as Double:  // n是Double类型
            print("is Double",n)
        default:
            break
        }
```

## 表达式模式(Expression Pattern)

1. 表达式模式用在case中

2.  

   ```swift
   switch point {
      case (-2...2, -2...2):  // 表达式模式
         print("哈哈哈哈")
      default：
        break
   }
   ```

3. 自定义表达式模式

   1. 可以通过重载运算符，自定义表达式模式的匹配规则

   2.  

      ```swift
      struct Student {
          var score = 0, name = ""
       
          // pattern: case 后面的内容
          // value： switch后面的内容
          static func ~= (pattern: Int, value: Student) -> Bool {
              value.score >= pattern
          }
          static func ~= (pattern: Range<Int>, value: Student) -> Bool {
              pattern.contains(value.score)
          }
       
          static func ~= (pattern: ClosedRange<Int>, value: Student) -> Bool {
              pattern.contains(value.score)
          }
       
      }
       
       
      // 使用
       var stu = Student(score: 88, name: "Jack")
              switch stu {
              case 100:
                  print(">=100")
              case 90...100:
                  print(">=90")
              case 80..<90:
                  print("[80-90)")
              default:break
              }
      ```

   3. 运用2

   4. ```swift
      extension String {
       
          static func ~= (pattern: (String) -> Bool, value: String) -> Bool {
       
              pattern(value)
          }
      }
       
      func hasPrefix(_ prefix: String) -> ((String) -> Bool) { { $0.hasPrefix(prefix) } }
      func hasSuffix(_ suffix: String) -> ((String) -> Bool) { { $0.hasSuffix(suffix) } }
       
      let str = "123456899"
      switch str {
      case hasPrefix("123"),hasSuffix("456"):
          print("以123开头或者以456j结尾")
      default:
          break
      }
      ```

## where

1. 可以使用where为模式匹配增加匹配条件

```swift
var ages1 = [10,20,44,23,55]
        for age1 in ages1 where  age1 > 30 {
            print(age1)
        }
```

#从Oc到Swift

## MARK、TODO、FIXME

1. // MARK: 类似于Oc中的 #pragma mark 
2. // MARK: - 类似于Oc中的 #pragma mark -
3. // TODO: 用于标记未完成的任务
4. // FIXME: 用于标记待修复的问题
5. // #warning("undo")  用TODO不是特明显，也可以用警告

## 条件编译

```swift
// 自定义编译标记： Build Setting 下搜 swift compiler - custom
// 方法一:本来有一个DEBUG，也可以自己加，空格+名字，就定义了自己名字的标记
// 方法二: 在Other Swift Flags下添加 -D+空格+名字
 
 
// 操作系统: macOS\iOS\tvOS\watchOS\Linux\Android\Windows\FreeBSD
#if os(macOS) || os(iOS)
 
 
// CPU架构: i386\x86_64\arm\arm64
#elseif arch(arm64) || arch(x86_64)
 
 
// swift版本
#elseif swift(<5) && swift(>=3)
 
 
// 模拟器
#elseif targetEnvironment(simulator)
 
 
// 可以导入某模块
#elseif canImport(Foundation)
 
 
#else
#endif
```

## log的封装

```swift
// #file , #function, #line  // 所在文件、方法、函数
func log<T>(_ msg: T,
            file: NSString = #file,
            line: Int = #line,
            fn: String = #function) {
 
    #if DEBUG
    let prefix = "\(file.lastPathComponent)_\(line)_\(fn):"
    print(prefix, msg)
    #endif
}
```

## 系统版本检测

```swift
if #available(iOS 10, macOS 10.12, *) {
 
    // 对于iOS平台，只在iOS10及以上版本执行
    // 对于macOS平台，只在macOS 10.12及以上版本执行
    // 最后的*表示在其他所有平台都执行
}
```

## API可用性说明

```swift
// person这个类只能iOS10，macOS 10.15以上才能用
@available(iOS 10, macOS 10.15,*)
class Person {
     
    // 当调用study_()的时候会报错，说这个方法已经改名了，改为study
    @available(*,unavailable,renamed: "study")
    func study_()  { }
    func study()  { }
    
    @available(iOS, deprecated: 11)
    @available(macOS,deprecated: 10.12)
    func run() { }  // 这个方法过期了
}
```

## iOS程序的入口

1. 在AppDelegate上面默认有个

   @UIApplicationMain

   标记，这表示

   1. 编译器自动生成入口代码(main函数代码),自动设置AppDelegate为App的代理

2. 也可以删掉

   @UIApplicationMain，

   自定义入口代码:

   1. 新建立一个main.swift文件

   ```swift
   import UIKit
   // 可以自己自定义个UIApplication
   class MJApplication: UIApplication{}
   UIApplicationMain(CommandLine.argc,
                     CommandLine,unsafeArgv,
                     NSStringFromClass(MJApplication.self), // 作为
                     NSStringFromClass(appDelegate.sel)) // 作为代理
   ```

## Swift调用OC

1. 新建一个桥接头文件，文件名格式默认为:{targetName}-Bridging-Header.h

2. Build Setting下搜bridging，找到Objective-C Bridging Header把这个文件路径放入

3. 也可以由系统帮忙生成，Swift项目里新建立Oc文件，会自动弹出让选bridging

4. 在{targetName}-Bridging-Header.h文件中#import  .h文件，就可以在Swift中使用了

5. MJPerson(age: 20, name: "20")会先找Oc中该类的实例方法，没有再找类方法，类方法直接通过类.function调用，实例方法通过实例.function调用

6. 

7. 如果C语言暴露给Swift的函数名跟Swift中的其他函数名冲突了

8. 1. 可以在Swift中使用**@_silgen_name**修改C函数名
   2. **@_silgen_name("sum")** **func** swift_sum(**_** v1: Int32, **_** v2: Int32) -> Int32 // 将Oc中C语言方法sum方法修改别名为Swift_sum

9.  

10. Oc调用Swift和Swift调用Oc互调一样

## OC调用Swift

1. Xcode已经默认生成一个用于OC调用Swift的头文件，文件名格式是:{targetName}-Swift.h
2. Build Setting下搜generated interface，找到Objective-C Generated interface Header Name有这个文件路径
3. Swift类要想暴露给Oc，需要继承NSObject，类里哪些东西需要暴露给Oc，需要在方法、属性前面加上@objc,Swift类前面加上@objcMembers说明该类里所有属性都暴露给Oc，就可以

```swift
// @objc(MJCar)  // 如果写上这句，OC调用这个类会当成MJCar来调用
// 原本swift中类前面8个自己就不再是MetalType数据，第二个是引用计数，然后依次是成员变量
// 继承自NSObject后，前8个是isa，然后依次是成员变量，所以大小会比swift方式小一点
@objcMembers class Car: NSObject {
    var price: Double
    // @objc(name) // Oc中使用band属性通过.name
    var band: String
    @objc init(price: Double, band: String) {
        self.price = price
        self.band = band
    }
    // @objc(drive) // Oc中调用run方法通过drive
   // @objc(exec:v2:) // Oc中调用run方法通过 [c exec:10 v2:20]
    func run() { print(price, band, "run") }
    static func  run() { print("Car run") }
}
 
 
// 在OC中使用
Car *car = [[Car alloc] initWithPrice:10 band:@"20"];
[car run];  // run方法不需要额外写@objc,而且Swift中的扩展会变成OC中的分类
```

4. 可以通过@objc重命名Swift暴露给Oc的符号名(类名、属性名、函数名等)
5. Oc调用Swift和Swift调用Oc互调一样

### 选择器(Selector)

1. Swift中依然可以使用选择器，使用#selector(name)定义一个选择器
   1. 必须是被@objcMembers或@objc修饰的方法才可以定义选择器

## 思考

1. 为什么Swift暴露给OC的类最终都要继承自NSObject?
   1. 因为OC调用方法是通过isa指针，所以必须继承NSObject，才能有isa指针
2. Swift调用OC方法底层是怎么操作的?
   1. 纯Swift调用方法通过虚表机制
   2. Swift调用OC方法，底层是调用msg_send，runtime机制
3. OC调用Swift方法底层怎么操作？
   1. 底层是调用msg_send，runtime机制，因为他继承了NSObject
4. Swift中调用了@objcMembers以及继承NSObject(即能被OC调用)的方法底层是怎么调用的?
   1. Swift调用依然是通过虚表机制(比调用runtime机制性能要好，因为不用通过isa找方法)
   2. 如果想走runtim机制，在方法前面加上修饰符: dynamic

## String

1. OC调用Swift的String会把它转换成NSString， Swift调用Oc的NSString会把它转换成String
2. Swift的字符串String和OC的NSString，在API设计上差异较大

```swift
var str: String = "1"
str.append("-2")
str = str + "-3" // String重载了运算符
str += "-4"  // String重载了运算符
str = "\(str)-5" // 插值
print(str,str.count) // 长度
str.hasPrefix("123") // 前缀
str.hasSuffix("456") // 后缀
 
 
// 和OC差异较大
// String的插入和删除
str = "1-2"
// str.startIndex 是 String.Index，是结构体String内部定义了结构体Index
// str.startIndex 是1的位置
// str.endIndex 是2后面的位置，不是2的位置
print(str,str.startIndex,str.endIndex) // 1-2 Index(_rawBits: 1) Index(_rawBits: 196609)
str.insert("_", at: str.endIndex)  // 1-2-
str.insert(contentsOf: "3-4", at: str.endIndex) // 1-2-3-4
str.insert(contentsOf: "666", at: str.index(after: str.startIndex)) // 1666-2-3-4
str.insert(contentsOf: "888", at: str.index(before: str.endIndex)) // 1666-2-3-8884
str.insert(contentsOf: "hello", at: str.index(str.startIndex, offsetBy: 4)) // 1666hello-2-3-8884
 
 
str.remove(at: str.firstIndex(of: "1")!) // 666hello-2-3-8884
str.removeAll{ $0 == "6" } // hello-2-3-8884
var range = str.index(str.endIndex, offsetBy: -4)..<str.index(before: str.endIndex)
str.removeSubrange(range) // hello-2-3-4
```

## Substring

1. String可以通过下标、prefix、suffix等截取子串，子串类型不是String，而是Substring
2. Substring也有拼接等方法
3. 底层SubString和String是共用一块地址，记着自己的那一截位置，并没有深拷贝，当Substring调用拼接等方法改变数据内容时，就会深拷贝一份

```swift
var str = "1-2-3-4-5"
var subStr1 = str.prefix(3) // 1-2
var subStr2 = str.suffix(3) // 4-5
 
var range = str.startIndex ..< str.index(str.startIndex, offsetBy: 3)
var subStr3 = str[range] // 1-2
 
print(subStr3.base)  //  最初的1-2-3-4-5
 
var str2 = String(subStr3) // SubString -> String 创建新String
print(str2)
```

## String相关的协议

1. BidirectionalCollection 协议包含的部分内容
   1. startIndex、endIndex属性、index方法
   2. String、Array都遵守了这个协议
2. RangeReplaceableCollection协议包含的部分内容
   1. append、insert、remove方法
   2. String、Array都遵守了这个协议

## 多行String

1. BidirectionalCollection 协议包含的部分内容
2. 多行String，以""" 开头 """结尾，中间包含的所有包括空格、换行都是String的组成

```swift
let str = """
  1"
   "2"
 jfldsj
        -3-
"""  // 结尾的第一个"会认为是起始位置，锁着这里1"前面是有空格的
```

## String与NSString

1. String与NSString之间可以随时随地桥接转换 // 用as 或 var str: NSString 就可以
2. String转NSString汇编可看到底层调用Swift.String.bridgeToObjectiveC()
3. 桥接转换之后，生成的新str是深拷贝，如var str3 = str1 as NSString // str3和str1地址不一样，str1是String类型，可以拼接，str3是不可变的，它的append方法是返回了一个新str
4. String 用 === ，NSString使用isEqual方法也可以===(本质还是调用了isEqual)，来比较内容是否等价
5. String不能转NSMutableString，NSMutableString可以转String
6. 同理Array和NSArray、NSMutableArray，Dictionary、Set等

#协议

## 只能被class继承的协议

1. **protocol** Runable1: AnyObject { }
2. **protocol** Runable2: class { }
3. **@objc** **protocol** Runable3{ }  // 被@objc修饰的协议，还可以暴露给Oc去遵守实现

## 可选协议

1. 可以通过@objc定义可选协议，这种协议只能被class遵守

## dynamic

1. 被@objc dynamic修饰的内容会具有动态性，比如调用方法会走runtime那一套流程

## KVC\KVO

1. 被Swift支持kv\kvo的条件
   1. 属性所在的类、监听器最终继承自NSObject
   2. 用@objc dynamic修饰对应的属性

### block方式的KVO

```swift
class Person: NSObject {
   @objc dynamic var age = 10
    var weight = 20
    var height = 100
 
    var observer: Observer = Observer()
    override init() {
        super.init()
        observe(\Person.age, options: .new) { (person, change) in
            print(change.newValue as Any)
        }
        self.addObserver(observer, forKeyPath: "height", options: .new, context: nil)
    }
 
}
 
 
class Observer: NSObject {
 
    override class func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        print("observeValue", change?[.newKey] as Any)
    }
}
```

## 关联对象

1. 在Swift中，class依然可以使用关联对象
   1. 默认情况下，swift中扩展不能添加成员属性，只能添加计算属性
2. 借助关联对象，可以实现类似extension为class增加存储属性的效果

```swift
class Person: NSObject {
 
}
 
 
extension Person {
 
    private static var AGE_KEY: Bool = false  // 也可以用void，都只占用一个字节，省内存
 
    var age: Int {
        get {
            print(Mems.memStr(ofVal: &Self.AGE_KEY))
           return objc_getAssociatedObject(self, &Self.AGE_KEY) as? Int ?? -1
        }
        set {
 
            objc_setAssociatedObject(self, &Self.AGE_KEY, newValue, .OBJC_ASSOCIATION_ASSIGN)
        }
    }
}
```

### 资源名管理

1. 方法一: 参考Android的资源名管理方式

```swift
enum R {
    enum string: String {
       case add = "添加"
    }
    enum image: String {
       case logo   // 原始值，默认是"logo"
       case cancel
    }
    enum seque: String {
       case login_main // 同上
    }
}
// 使用资源: R.string.add
```

2. 更多优秀的思路参考 https://github.com/mac-cain13/R.swift 和 https://github.com/SwiftGen/SwiftGen

# 多线程

## 多线程开发-异步-延迟

1. 如：DispatchQueue.global().async

### DispatchWorkItem用法

```swift
struct Asyncs {
    public typealias Task = () -> Void
 
    public static func async(_ task: @escaping Task) {
 
        _async(task)
    }
 
 
    public static func async(_ task: @escaping Task,
                             _ mainTask: @escaping Task) {
          _async(task,mainTask)
    }
 
 
    public static func _async(_ task: @escaping Task,
                             _ mainTask: Task? = nil) {
        let item = DispatchWorkItem(block: task)
        DispatchQueue.global().async(execute: item)
        if let main = mainTask {
            item.notify(queue: DispatchQueue.main, execute: main) // 通知主队列执行任务main
        }
    }
    // 延迟方法
    public static func delay(_ seconds: Double,
                             _ block: @escaping Task) -> DispatchWorkItem {
        let item = DispatchWorkItem(block: block)
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now()+seconds, execute: item)
        return item
    }
 
    // 异步延迟方法
    public static func asyncDelay(_ seconds: Double,
                             _ block: @escaping Task,
                             _ mainTask: Task? = nil) -> DispatchWorkItem {
        let item = DispatchWorkItem(block: block)
        DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+seconds, execute: item)
        if let main = mainTask {
            item.notify(queue: DispatchQueue.main, execute: main) // 通知主队列执行任务main
        }
        return item
    }
}
// 使用
Asyncs.async({
            print("子线程干的事情")
        }) {
           print("主线程干的事情")
        }
let item = Asyncs.delay(2) {
            print("延迟2秒执行")
        }
       // item.cancel() // 取消这个item任务
```

## 多线程开发-once

1. dispatch_once在Swift中已被废弃，取而代之
   1. 可以用 类型属性 或者 常量变量\常量
   2. 默认自带 lazy+dispatch_once效果

```swift
var age1: Int = {
    print(2)
    return 0
}()
 
 
class ViewController: UIViewController {
 
 
    // static表示是静态的(本质全局变量)，只会初始化一次，
    static var age: Int = {
       print(1)
       return 0
    }()
 
    override func viewDidLoad() {
        super.viewDidLoad()
        // 虽然调用多次，但是age里的print(1)只调用1次
        print(Self.age) // 1 0
        print(Self.age) // 0
        print(Self.age)  // 0
 
        print(age1) // 2 0
        print(age1)  // 0
    }
}
```

## 多线程开发-加锁

#函数式编程

1. 函数式编程是一种编程范式，也就是如何编写程序的方法论
2. 主要思想: 把计算过程尽量分解成一系列可复用函数的调用
3. 主要特征: 函数是"第一等公民"
4. 函数与其他数据类型一样的地位，可以赋值给其他变量，也可以作为函数参数、函数返回值
5. 函数式编程中几个常用的概念
   1. Higher-Order Function、Function Curring
   2. Functor、Applicative Functor、Monad
6. 函数式写法

```swift
// 假设x要实现以下功能: [(num+3)*5-1]%10/2]
let num = 1
 
 
// 接收一个参数，返回一个函数,高阶函数、柯里化
func add(_ v: Int) -> (Int) -> Int { { $0 + v } }  // $0是返回的函数的参数，
func sub(_ v: Int) -> (Int) -> Int { { $0 - v } }
func multiple(_ v: Int) -> (Int) -> Int { { $0 * v } }
func divide(_ v: Int) -> (Int) -> Int { { $0 / v } }
func mod(_ v: Int) -> (Int) -> Int { { $0 % v } }
 
let fn1 = add(3)
let fn2 = multiple(5)
let fn3 = sub(1)
let fn4 = mod(10)
let fn5 = divide(2)
 
// 效果: fn5(fn4(fn3(fn2(fn1(num)))))
 
// 优化第一步
// 函数合成
func composite(_ f1: @escaping (Int) -> Int,
               _ f2: @escaping (Int) -> Int) -> (Int) -> Int {
 
    return  { f2(f1($0))}
}
let fn21 = composite(add(3), multiple(5))
 
// 优化第二步
infix operator >>> : AdditionPrecedence // 优先级和加法一样
//func >>>(_ f1: @escaping (Int) -> Int,
//         _ f2: @escaping (Int) -> Int) -> (Int) -> Int {
//
//    return  { f2(f1($0))}
//}
// 进一步优化
func >>><A, B, C>(_ f1: @escaping (A) -> B,
                  _ f2: @escaping (B) -> C) -> (A) -> C {
 
    return  { f2(f1($0))}
}
let fn31 = add(3) >>> multiple(5) >>> sub(1) >>> mod(10) >>> divide(2)
print(fn31(num))
```

## 高阶函数(Higher-Order Function)

1.  高阶函数是至少满足下列一个条件的函数：
   1. 接受一个或多个函数作为输入(map、filter、reduce等)
   2. 返回一个函数

## 柯里化(Currying)

1.  什么叫柯里化：
   1. 将一个接受多参数的函数变换成一系列只接受单个参数的函数
   2. Array、Optional的map方法接收的参数就是一个柯里化参数
2. 例子:

```swift
func add22(_ v1: Int) -> (Int) -> ((Int) -> Int) {
          {
            v2 in {
                print(v1,v2,$0)  // 1 2 3
                return v1 + $0 + v2
            }
          }
        }
 
 
        let fn1 = add22(1)(2)(3)
        print(fn1)  // 6
```

3. 柯里化模版，自动将接收2个函数的方法转成柯里化方式

```swift
func currying<A, B, C>(_ fn: @escaping (A, B) -> C) -> (B) -> (A) -> C {
 
            return { b in
                return { a in
                    return fn(a,b)
                }
            }
        }
```

## 函子(Functor)

1.  像Array、Optional这样支持map运算的类型，称为函子
2. 复合公式: **func** map<T>(**_** fn: (Inner) -> T) -> Type<T>都可以称为函子 // Inner指里头包装的东西
   1. Array: **func**map<T>(**_** fn: (Element) -> T) -> Array<T>
   2. Optional: **func**map<T>(**_** fn: (Wrapped) -> T) -> Optional<T>
3. 资料
   1. http://adit.io/posts/2013-04-17-functors,_applicatives,_and_monads_in_pictures.html
   2. http://www.mokacoding.com/blog/functor-applicative-monads-in-pictures/

## 适用函子(Applicative Functor)

1. 对任意一个函子F，如果能支持以下运算，该函子就是一个适用函子

   1. **func** pure<A>(**_** value: A) -> F<A> // 随便给一个值，都可以返回自己
   2. **func** <*><A,B>(fn:F<(A)->B>, value: F<A>) -> F<B>

2. Optional可以成为适用函子

   1. ```swift
      func pure<A>(_ value: A) -> A? { value }
      infix operator <*> : AdditionPrecedence
      func <*><A, B>(fn: ((A)->B)?, value: A?) -> B? {
         ``guard let f = fn, let v = value ``else` `{ ``return` `nil }
         ``return` `f(v)
      }
      // 适用
      var value: Int? = ``10
      var fn: ((Int) -> Int)? = { $``0` `* ``2` `}
      // Optional(20)
      print( fn <*> value as Any )
      ```

3. Array可以成为适用函子

## 单子(Monad)

1. 对任意一个类型F，如果能支持以下运算，那么就可以称为是一个单子
   1. **func** pure<A>(**_** value: A) -> F<A> // 随便给一个值，都可以返回自己
   2. **func** flatMap<A,B>(_ value: F<A>,_ fn:F<(A)->B>) -> F<B>
2. Optional、Array都可以称为单子

## 函数式编程(POP)

1. 是Swift的一种编程范式，Apple于2015年WWDC提出
2. 在Swift的标准库中， 能见到大量POP的影子
3. 同时Swift也是一门面对对象的编程语言(OOP)
   1. 在Swift开发中，OOP和POP是相辅相成的，任何一方并不能取代另一方
4. 面向协议思想优先考虑的不是写一个父类继承，而且协议

## 利用协议

1. 是Swift的一种编程范式，Apple于2015年WWDC提出
2. 在
3. 利用协议实现前缀效果

```swift
protocol MJCompatible { }
extension MJCompatible {
//    var mj: MJ<Self> { MJ(self) } // 只读计算属性，不会产生强引用，但是不能修改，也就是扩展功能不能是mutating方法
    var mj: MJ<Self> {
        set {}
        get { MJ(self) }
    } // 读写计算属性，不会产生强引用
    static var mj: MJ<Self>.Type { MJ.self }  // 返回类
}
// 让String拥有mj前缀属性
extension String: MJCompatible { }
extension NSString: MJCompatible { }
 
var str = "123rrr"
struct MJ<Base> {
    var base: Base
    init(_ base: Base) {
        self.base = base
    }
}
// 扩展功能
//extension MJ where Base == String {
extension MJ where Base == ExpressibleByStringLiteral {
    var numberCount: Int {
        var count = 0
        for c in (base as! String) where ("0"..."9").contains(c) {
            count += 1
        }
        return count
    }
    mutating func change() {
 
    }
    static func test() {
 
    }
}
"123".mj.numberCount
var str: NSString = "123"
str.mj.change()
```

4. 利用协议实现类型判断 （协议最终就是个具体类型）

```swift
protocol ArrayType {
 
}
extension Array: ArrayType { }
extension NSArray: ArrayType { }
 
func isArrayType(_ type: Any.Type) -> Bool { type is ArrayType.Type }
 
print(isArrayType([Int].self)) // true
print(isArrayType([Any].self)) // true
print(isArrayType(NSArray.self)) // true
```

## Array的常见操作

```swift
let arr = [1,2,3,4]
let arr2 = arr.map { $0 * 2 } // 遍历所有值*2
print(arr2,arr)  // [2, 4, 6, 8] [1, 2, 3, 4]
let arr3 = arr.filter{ $0 % 2 == 0 } // 过来能被2整除的数
print(arr3,arr)  // [2, 4] [1, 2, 3, 4]
 
// 可简写; let result = arr.reduce(0,+)
let result = arr.reduce(0) { (result, element) -> Int in  // 这里的Int和0一定是同一个类型，
      return result + element
}  // 先是0和第一个元素相加，得到的值给result，然后第二个元素和这个result相加，生成新result,以此类推。。。
print(result)  // 10
 
// map方法还可以这样使用
func double(_ i: Int) -> Int { i*2 }
print(arr.map(double))  // 因为arr里的元素是int类型，所以double的参数也需要是int，返回值可以任意
// Array.init(repeating: $0, count: $0)指创建$0个单位数组，每个值是$0
 var arr5 = arr.map{ Array.init(repeating: $0, count: $0) } // [[1], [2, 2], [3, 3, 3], [4, 4, 4, 4]]
 
// 会把多个数组整合成一个数组
var arr6 = arr.flatMap{ Array.init(repeating: $0, count: $0) } // [1, 2, 2, 3, 3, 3, 4, 4, 4, 4]
let arr7 = ["123","test","-30"]
let arr8 = arr7.map{ Int($0) }  // [Optional(123), nil, Optional(-30)]
// 将数组里的原始转成Int，转不了的清空
let arr9 = arr7.compactMap{ Int($0) } //[123, -30]
print(arr8,arr9)
```

## lazy的优化

1. arr.lazy.map // 是一种优化，当用到后面的数据时候，再循环那个位置

## Optional的map和flatMap

1. Optional也可以使用map，它会先解包，如果有值将解包后的值再调用闭包里的方法，如果解包后无知，直接返回nil
2. 当调用Optional的flatMap，闭包里是包装一个可选项，那么如果调用方自己就是Optional，就不会再包装，而直接用map,就会编程Optional(Optional(值))类型

# 响应式编程

## 响应式编程(Reactive Programming)RP

1. 于1997年提出，可以简化异步编程，提供更优雅的数据绑定
2. 一般与函数式融合在一起，所以也会叫做: 函数响应式编程(Functional Reactive Programming) FRP
3. 比较著名、成熟的响应式框架
   1. ReactiveCocoa：
      1. 简称RAC，有Oc、Swift版本
   2. ReactiveX
      1. 简称Rx，有众多编程语言的版本，RxJava、RxKotlin、RxGo、RxJS、RxCpp、RxCpp、RxSwift等等

## RxSwift

1. ReactiveX的Swift版本
2. 源码: https://github.com/ReactiveX/RxSwift
3. 中文文档: https://beeth0ven.github.io/RxSwift-Chinese-Documentation/
4. 模块说明
   1. RxSwift： Rx标准API的Swift实现，不包括任何iOS相关的内容
   2. RxCocoa: 基于RxSwift，给iOS UI控件扩展了很多Rx特性

### RxSwift的核心角色

1. Observable:负责发送事件(Event)
2. Observer:负责订阅Observable，监听Observable发送的事件(Event) 

![RxSwift](/Swift入门到精通笔记/RxSwift.png)

### 创建订阅Observable

1. Observable:负责发送事件(Event)

```swift
import UIKit
import Foundation
 
 
import RxSwift
import RxCocoa
  
    
class ViewController: UIViewController {
      
    var label: UILabel = UILabel(frame: CGRect(x: 100, y: 100, width: 100, height: 100))
     
    let bag = DisposeBag()
     
    override func viewDidLoad() {
        super.viewDidLoad()
         
        self.view.addSubview(label)
         
        // 常见消息
        var observable = Observable<Int>.create { (observer) -> Disposable in
             
            observer.onNext(1)
             observer.onNext(2)
             observer.onNext(3)
            observer.onCompleted()
            return Disposables.create()
        }
        // 另一种格式
//        observable = Observable.just(1)  // 只发一个，匹配一个onNext和一个onCompleted
//        observable = Observable.of(1,2,3)  // 可以发多次，匹配多个个onNext和一个onCompleted
//        observable = Observable.from([1,2])  // 可以发多次，匹配多个个onNext和一个onCompleted
//
        // 2秒之后每隔1秒发送一个消息
        let observable2 = Observable<Int>.timer(.seconds(2), period: .seconds(1), scheduler: MainScheduler.instance)
         
         
        // 订阅,订阅多个，多个都会走
        observable.subscribe{ event in
            print(event)
        }.dispose()
          
        observable.subscribe(onNext: {
            print("next",$0)
        }, onError: {
            print("error",$0)
        }, onCompleted: {
            print("completed")
        }, onDisposed: {
            print("dispose")
        }).dispose()
         
        observable.subscribe(onNext: {
            print("next",$0)
        }, onError: {
            print("error",$0)
        }, onCompleted: {
            print("completed")
        }, onDisposed: {
            print("dispose")
            }).disposed(by: bag) // 当bag销毁时，调用Disposable实例的dispose, 这里是当这个页面销毁时，bag就销毁了
         
        // 当self销毁时，调用Disposable实例的dispose
       let _ = observable.takeUntil(self.rx.deallocated).subscribe(onNext: {
            print("next",$0)
        }, onError: {
            print("error",$0)
        }, onCompleted: {
            print("completed")
        }, onDisposed: {
            print("dispose")
            })
         
        // 先用map将接收到的值包装成字符串，再将值绑定到label上，这样label就有计时数字值
      let _ = observable2.takeUntil(self.rx.deallocated).map{ "\($0)" }.bind(to: label.rx.text)
    }
}
```

### Disposable

1. 每当Observable被订阅时，都会返回一个Disposable实例，当调用Disposable的dispose，就相当于取消订阅,上例中有3种方式取消订阅

## 创建Observer

1. 除了Observable订阅的方式，也可以自己创建Observer实现监听

```swift
let observer = AnyObserver<Int>.init{ event in
     switch event {
        case .next(let data):
          print(data)
        case .completed:
          print("completed")
        case .error(let error):
          print("error",error)
        }
}
Observable.just(1).subscribe(observer).dispose()
 
let binder = Binder<String>(label) { label, text in
    label.text = text
}
Observable.just(1).map{ "\($0)" }.subscribe(binder).dispose()
```

## RxSwift的状态监听

1. UI控件，tableView都可以利用RxSwift写，再也不用写tableview.delegate = **self，**如果tableView用了RxSwift就不能用tableview.delegate = self，否则RxSwift会无效

## 即是Observable、又是Observer

1. 诸如UISlider.rx.value、UITextField.rx.text这类属性值，即是Observable、又是Observer
2. 它们是RxCocoa.ControlProperty类型

