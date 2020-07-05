//
//  ScrollViewController.swift
//  ScrollViewTest
//
//  Created by 周素华 on 29/6/2020.
//  Copyright © 2020 周素华. All rights reserved.
//

import Foundation
import UIKit

class ScrollViewController: UIViewController {
 
    
    var topView: TopView?
    var tableController: TableViewController?
    
    override func viewDidLoad() {
        super.viewDidLoad()
         
        self.view.backgroundColor = UIColor.white
         
        createUI()
        
        
    }
    
    func createUI() {
        
        topView = TopView(frame: CGRect(x: 10, y: 10, width: 400, height: 100))
        self.view.addSubview(topView!)
        
        tableController = TableViewController.init()
        tableController?.view.frame = CGRect(x: 10, y: 150, width: 400, height: 700)
        self.view.addSubview((tableController?.view!)!)
        self.addChild(tableController!)
        
        
        self.view.bounds = CGRect(x: 20, y: 20, width: self.view.frame.size.width, height: self.view.frame.size.height)
    }
    
    
    
}



