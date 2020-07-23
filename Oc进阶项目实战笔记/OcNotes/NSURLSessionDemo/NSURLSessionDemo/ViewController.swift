//
//  ViewController.swift
//  NSURLSessionDemo
//
//  Created by 周素华 on 2020/7/23.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        SessionDataTask.postDemo()
        
        
        // 直接创建
//        var session = URLSession.shared
        
        // 配置后创建
       // URLSession.init(configuration: <#T##URLSessionConfiguration#>)
        
        // 设置加代理获得
        // 使用代理方法需要设置代理,但是session的delegate属性是只读的,要想设置代理只能通过这种方式创建session

//        var session =URLSession.init(configuration: URLSessionConfiguration.default, delegate: self, delegateQueue: OperationQueue.init());
   
        /*
         关于NSURLSession的配置有三种类型：

         //默认的配置会将缓存存储在磁盘上
         + (NSURLSessionConfiguration *)defaultSessionConfiguration;

         //瞬时会话模式不会创建持久性存储的缓存
         + (NSURLSessionConfiguration *)ephemeralSessionConfiguration;

         //后台会话模式允许程序在后台进行上传下载工作
         + (NSURLSessionConfiguration *)backgroundSessionConfigurationWithIdentifier:(NSString *)identifier
          
         */
        
        // 创建task
     
       // 1）NSURLSessionDataTask
       /*
        通过request对象或url创建：

        - (NSURLSessionDataTask *)dataTaskWithRequest:(NSURLRequest *)request;

        - (NSURLSessionDataTask *)dataTaskWithURL:(NSURL *)url;
         
        
         通过request对象或url创建，同时指定任务完成后通过completionHandler指定回调的代码块
         - (NSURLSessionDataTask *)dataTaskWithRequest:(NSURLRequest *)request completionHandler:(void (^)(NSData *data, NSURLResponse *response, NSError *error))completionHandler;

         - (NSURLSessionDataTask *)dataTaskWithURL:(NSURL *)url completionHandler:(void (^)(NSData *data, NSURLResponse *response, NSError *error))completionHandler;
        */
        
        
        //NSURLSessionUploadTask
        /*
         通过request创建，在上传时指定文件源或数据源：

         - (NSURLSessionUploadTask *)uploadTaskWithRequest:(NSURLRequest *)request fromFile:(NSURL *)fileURL;
            
         - (NSURLSessionUploadTask *)uploadTaskWithRequest:(NSURLRequest *)request fromData:(NSData *)bodyData;
           
         - (NSURLSessionUploadTask *)uploadTaskWithStreamedRequest:(NSURLRequest *)request;
          
         
         通过completionHandler指定任务完成后的回调代码块：

         - (NSURLSessionUploadTask *)uploadTaskWithRequest:(NSURLRequest *)request fromFile:(NSURL *)fileURL completionHandler:(void (^)(NSData *data, NSURLResponse *response, NSError *error))completionHandler;

         - (NSURLSessionUploadTask *)uploadTaskWithRequest:(NSURLRequest *)request fromData:(NSData *)bodyData completionHandler:(void (^)(NSData *data, NSURLResponse *response, NSError *error))completionHandler;
         */
        
        // NSURLSessionDownloadTask
        /*
         下载任务支持断点续传，第三种方式是通过之前已经下载的数据来创建下载任务：

         - (NSURLSessionDownloadTask *)downloadTaskWithRequest:(NSURLRequest *)request;
             
         - (NSURLSessionDownloadTask *)downloadTaskWithURL:(NSURL *)url;
           
         - (NSURLSessionDownloadTask *)downloadTaskWithResumeData:(NSData *)resumeData;
         同样地可以通过completionHandler指定任务完成后的回调代码块：

         - (NSURLSessionDownloadTask *)downloadTaskWithRequest:(NSURLRequest *)request completionHandler:(void (^)(NSURL *location, NSURLResponse *response, NSError *error))completionHandler;

         - (NSURLSessionDownloadTask *)downloadTaskWithURL:(NSURL *)url completionHandler:(void (^)(NSURL *location, NSURLResponse *response, NSError *error))completionHandler;

         - (NSURLSessionDownloadTask *)downloadTaskWithResumeData:(NSData *)resumeData completionHandler:(void (^)(NSURL *location, NSURLResponse *response, NSError *error))completionHandler;
         */
        
        // 我们在使用三种 task 的任意一种的时候都可以指定相应的代理。NSURLSession 的代理对象结构如下：
        /*
         
               NSURLSessionDelegate – 作为所有代理的基类，定义了网络请求最基础的代理方法。

               NSURLSessionTaskDelegate – 定义了网络请求任务相关的代理方法。

               NSURLSessionDownloadDelegate – 用于下载任务相关的代理方法，比如下载进度等等。

               NSURLSessionDataDelegate – 用于普通数据任务和上传任务。
         */
        
        
    }


}

