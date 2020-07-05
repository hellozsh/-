//
//  ViewController.swift
//  ScrollViewTest
//
//  Created by 周素华 on 29/6/2020.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    var num = 0
    var superView: UIView?
    
    override func viewDidLoad() {
        super.viewDidLoad()
         
        self.view.backgroundColor = UIColor.white
         
        
        superView = UIView(frame: CGRect(x: 10, y: 100, width: 300, height: 600))
        superView?.bounds = CGRect(x: 10, y: 10, width: 300, height: 400)
        superView!.backgroundColor = UIColor.lightGray
        self.view.addSubview(superView!)
        
        for i in 0...5 {
//
            let subView = UIView(frame: CGRect(x: 0, y: i*40, width: 200, height: 40))

            let R = (arc4random() % 256) ;
            let G = (arc4random() % 256) ;
            let B = (arc4random() % 256) ;

           subView.backgroundColor = UIColor(red: CGFloat(R)/255.0, green: CGFloat(G)/255.0, blue: CGFloat(B)/255.0, alpha: 1)
           superView!.addSubview(subView)
        }
        

//
//
//        let lineView = UIView(frame: CGRect(x: 0, y: 10, width: 200, height: 200))
//        lineView.backgroundColor = UIColor.blue
//        superView.addSubview(lineView)
//        // Do any additional setup after loading the view.
        
        superView!.clipsToBounds = true
    }

    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        num += 1
        
        var bound = superView?.bounds
        bound?.origin.y = CGFloat(num * 30)
        superView?.bounds = bound!
        
    }
    

}

