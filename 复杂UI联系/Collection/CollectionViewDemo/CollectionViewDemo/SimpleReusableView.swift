//
//  SimpleReusableView.swift
//  CollectionViewDemo
//
//  Created by 周素华 on 2020/8/16.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit

class SimpleReusableView: UICollectionReusableView {
        
    let width = UIScreen.main.bounds.size.width//获取屏幕宽
       var _titleLabel:UILabel?//title
    var num = ""
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        initView()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func initView(){
        _titleLabel = UILabel(frame: CGRect(x:5, y:5, width: (width-40)/2, height: 50))
        self .addSubview(_titleLabel!)
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        num = ""
    }
    
    open func refreshData() {
        _titleLabel?.text = num;
    }
          
}
