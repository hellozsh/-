//
//  SimpleCollectionViewCell.swift
//  CollectionViewDemo
//
//  Created by 周素华 on 2020/8/16.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit

class SimpleCollectionViewCell: UICollectionViewCell {
    
    let width = UIScreen.main.bounds.size.width//获取屏幕宽
       var titleLabel:UILabel?//title
       
       
       override init(frame: CGRect) {
           super.init(frame: frame)
           initView()
       }
       
       required init?(coder aDecoder: NSCoder) {
           fatalError("init(coder:) has not been implemented")
       }
        
       
       func initView(){
    
           
          titleLabel = UILabel(frame: CGRect(x:5, y:5, width: (width-40)/2, height: 50))
           self .addSubview(titleLabel!)
       }
}
