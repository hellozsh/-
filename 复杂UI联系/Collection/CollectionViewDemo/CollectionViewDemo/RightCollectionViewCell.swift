//
//  RightCollectionViewCell.swift
//  CollectionViewDemo
//
//  Created by 周素华 on 2020/8/17.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit

class RightCollectionViewCell: UICollectionViewCell {
    let width = UIScreen.main.bounds.size.width//获取屏幕宽
    var titleLabel:UILabel?//title
    var numLabel: UILabel?
       
    override init(frame: CGRect) {
        super.init(frame: frame)
        initView()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func initView(){
        titleLabel = UILabel(frame: CGRect(x:0, y:5, width: (self.bounds.size.width-20), height: 40))
        self.addSubview(titleLabel!)
        titleLabel?.backgroundColor = UIColor.lightGray
        
        numLabel = UILabel(frame: CGRect(x: -10, y: 10, width: 28.3, height: 28.3))
        numLabel?.backgroundColor = UIColor.white
        numLabel?.transform = CGAffineTransform(rotationAngle: 40);
        self.addSubview(numLabel!);
    }
}
