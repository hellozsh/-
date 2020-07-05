//
//  TopView.swift
//  ScrollViewTest
//
//  Created by 周素华 on 29/6/2020.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit

class TopView: UIView {

    var label: UILabel?
    var image: UIImageView?
    
    override init(frame: CGRect) {
        
         super.init(frame: frame)
        
    
        
    
           createUI()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }
    
    func createUI() {
        
        label = UILabel(frame: CGRect(x: 20, y: 40, width: 200, height: 40))
        label?.text = "亢金龙"
        self.addSubview(label!)
        
        image = UIImageView(frame: CGRect(x: 300, y: 40, width: 20, height: 20))
        image?.image = UIImage(named: "tips")
        self.addSubview(image!)
        
        self.backgroundColor = UIColor.yellow
    }
}
