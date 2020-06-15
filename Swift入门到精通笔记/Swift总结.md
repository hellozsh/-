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

