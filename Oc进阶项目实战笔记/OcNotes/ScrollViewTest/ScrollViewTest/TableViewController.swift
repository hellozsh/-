//
//  tableViewController.swift
//  ScrollViewTest
//
//  Created by 周素华 on 29/6/2020.
//  Copyright © 2020 周素华. All rights reserved.
//

import Foundation
import UIKit

class TableViewController: UIViewController {

    var tableView: UITableView?
       
       override func viewDidLoad() {
           super.viewDidLoad()
            
           self.view.backgroundColor = UIColor.lightGray
            
           createUI()
           
           
       }
       
       func createUI() {
           
        tableView = UITableView(frame: CGRect(x: 0, y: 0, width: 400, height: 300))
           self.view.addSubview(tableView!)
        tableView?.backgroundColor = UIColor.blue
        
       }
    
}
