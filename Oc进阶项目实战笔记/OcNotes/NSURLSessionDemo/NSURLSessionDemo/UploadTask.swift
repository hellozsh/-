//
//  UploadTask.swift
//  NSURLSessionDemo
//
//  Created by 周素华 on 2020/7/23.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit

class UploadTask {
    
    // 以数据流的方式进行上传
    static func uploadStreamDemo(){
        
        let session = URLSession.shared
            guard let url = URL(string: "https://api.test.xzlcorp.com/v0/patient/user/verification") else {
                return
            }
        var request = URLRequest.init(url: url)
        request.httpMethod = "POST";
        
        var data = Data()
        do {
            
             data = try Data.init(contentsOf: URL(string: "/Users/lifengfeng/Desktop/test.jpg")!)
        } catch {
            
        }
        // 开始上传
        let task = session.uploadTask(with: request,
                                      from: data, completionHandler: { (data, respon, error) in
          
          if(error == nil) {
              
               print("成功")
          } else {
              print("失败")
          }
         }
        )
        task.resume()
        
    }
    
    // 以拼接表单的方式进行上传
    /*
     上传的关键是请求体部分的表单拼接，获取本地上传文件的类型（MIME Types），至于具体的网络上传则很简单。 另外拼接表单的方式会有大小限制，即HTML的MAX_FILE_SIZE限制(可以自己设定，一般2MB)。
     根据上面的继承关系图，我们知道uploadTask是dataTask的子类，也可以使用uploadTask来代替dataTask。
     */
    /*
     表单拼接格式如下，boundary作为分界线：

     --boundary
     Content-Disposition:form-data;name=”表单控件名称”;filename=”上传文件名称”
     Content-Type:要上传文件MIME Types

     要上传文件二进制数据;

     --boundary--
     示例代码如下：
     */
    static func uploadFormDemo() {
        
        var urlStr = "http://localhost/upload/upload.php"
        urlStr.addingPercentEncoding(withAllowedCharacters: .urlFragmentAllowed)
        
        guard let url = URL(string: urlStr) else {
                return
            }
        
        var request = URLRequest.init(url: url)
        request.httpMethod = "POST";
        
        let contentType = String.init(format: "multipart/form-data; boundary=%@", "boundary")
        request.setValue(contentType, forHTTPHeaderField: "Content-Type")
        
        // 3.拼接表单，大小受MAX_FILE_SIZE限制(2MB)  FilePath:要上传的本地文件路径  formName:表单控件名称，应于服务器一致
        let data = self.getHttpBody(filePath: "/Users/lifengfeng/Desktop/test.jpg", formName: "file", reNa: "newName.png")
        request.httpBody = data
        
       // 根据需要是否提供，非必须,如果不提供，session会自动计算
        request.setValue(String.init(format: "%lu", data.count), forHTTPHeaderField: "Content-Length")
        
        // 4.1 使用dataTask
        URLSession.shared.dataTask(with: request) { (data, respon, error) in
            if(error == nil) {
                
                 print("成功")
            } else {
                print("失败")
            }
        }.resume()
        
        
        // 或4.2 开始上传 使用uploadTask   fromData:可有可无，会被忽略
        URLSession.shared.uploadTask(with: request, from: nil) { (data, respon, error) in
            if(error == nil) {
                
                 print("成功")
            } else {
                print("失败")
            }
        }.resume()
    }
    
  
    
    static func getHttpBody(filePath: String,formName: String?, reNa: String) -> Data {
        
       var data = Data()
        let response = self.getLocalFileRespon(urlStr: filePath)
        let fileType = response.mimeType
        
        var reName = reNa
        if reNa == nil {
            reName = response.suggestedFilename!
        }
        
        //表单拼接
        var headerStrM = String()
       headerStrM =  headerStrM.appendingFormat("--%@\r\n", "boundary")
        // name：表单控件名称  filename：上传文件名
        headerStrM = headerStrM.appendingFormat("Content-Disposition: form-data; name=%@; filename=%@\r\n", formName as! CVarArg, reName)
        headerStrM = headerStrM.appendingFormat("Content-Typ: %@\r\n", fileType!)
        data.append(headerStrM.data(using: .utf8)!)
        
        //文件内容
        do {
            let fileData = try Data.init(contentsOf: URL(string: filePath)!)
             data.append(fileData)
            
            let footerStrM = String.init(format: "\r\n--%@--\r\n", "boundary")
            data.append(footerStrM.data(using: .utf8)!)
            
            return data
        } catch {
        }
        return Data()
    }
    
    static func getLocalFileRespon(urlStr: String) -> URLResponse {
        
        let urlString = urlStr.addingPercentEncoding(withAllowedCharacters: .urlFragmentAllowed)
        let url = URL.init(fileURLWithPath: urlString!)
        let request = URLRequest.init(url: url)
        
        let semaphore = DispatchSemaphore(value: 0)
        var localResponse: URLResponse?
        URLSession.shared.dataTask(with: request) { (data, respon, error) in
            
            localResponse = respon!
            semaphore.signal();
            
        }.resume()
        semaphore.wait()
        return localResponse!
    }
}


/*
 NSURLSessionUploadTask *task =
 [[NSURLSession sharedSession] uploadTaskWithRequest:request
                                            fromFile:fileName
                                   completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
 }];
 
 
 
 [self.session uploadTaskWithRequest:request
                            fromData:body
                   completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
 NSLog(@"-------%@", [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:nil]);
 }];
 
 
 */
