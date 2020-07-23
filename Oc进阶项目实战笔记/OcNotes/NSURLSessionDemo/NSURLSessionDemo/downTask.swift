//
//  downTask.swift
//  NSURLSessionDemo
//
//  Created by 周素华 on 2020/7/23.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit

class downTask: NSObject {

    static func downDemo() {
           
        var urlStr = "http://www.xxx.com/test.mp3"
        urlStr.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
        
        guard let url = URL(string: urlStr) else {
                return
            }
        
        var request = URLRequest.init(url: url)
        
        URLSession.shared.downloadTask(with: request) { (location, respon, error) in
            
            if(error == nil) {
                // location:下载任务完成之后,文件存储的位置，这个路径默认是在tmp文件夹下!
                // 只会临时保存，因此需要将其另存
                print("location:",location?.path)
                print("成功")
            } else {
                print("失败")
            }
        }.resume()
    }
}
