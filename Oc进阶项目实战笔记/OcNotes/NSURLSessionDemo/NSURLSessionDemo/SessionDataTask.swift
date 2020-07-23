//
//  SessionDataTask.swift
//  NSURLSessionDemo
//
//  Created by 周素华 on 2020/7/23.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit

class SessionDataTask {

   static func getDemo() {
        
        let session = URLSession.shared
        guard let url = URL(string: "") else {
            return
        }
       let task = session.dataTask(with: url) { (data, respon, error) in
            
            print(String(data: data!, encoding: .utf8) ?? "null-----")
            
            do{
                let data = try JSONSerialization.data(withJSONObject: data!, options: .prettyPrinted)
                print(data)
            } catch {
            
                print("1234567890")
            }
        }
        task.resume()
    }
    
   static func postDemo() {
        
        let session = URLSession.shared
        guard let url = URL(string: "https://api.test.xzlcorp.com/v0/patient/user/verification") else {
            return
        }
        
        var request = URLRequest.init(url: url)
        request.httpMethod = "POST";
        let data = "username=18310099271&action=resetpwd".data(using: .utf8)
        request.httpBody = data
        
       let task = session.dataTask(with: request) { (data, respon, error) in
        
        if(error == nil) {
            
             print("成功")
        } else {
            print("失败")
        }
       }
      task.resume()
    }
    
    
    
}
