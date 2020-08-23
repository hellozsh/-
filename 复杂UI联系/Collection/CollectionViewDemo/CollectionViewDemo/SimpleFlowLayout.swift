//
//  SimpleFlowLayout.swift
//  CollectionViewDemo
//
//  Created by 周素华 on 2020/8/17.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit

class SimpleFlowLayout: UICollectionViewFlowLayout {

    override init() {
        super.init()
          
        
        self.itemSize = CGSize(width: UIScreen.main.bounds.size.width/2, height: 40)
        self.headerReferenceSize = CGSize(width: UIScreen.main.bounds.size.width, height: 30)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
//     这个属性数组包含了所有视图的布局信息，包括 cell,supplementary view,decortation view
    override func layoutAttributesForElements(in rect: CGRect) -> [UICollectionViewLayoutAttributes]? {
        let array = super.layoutAttributesForElements(in: rect)
        let newArray = NSMutableArray.init()
        var preY: CGFloat = 0.0
        for attrs in array! {
            let newAttrs: UICollectionViewLayoutAttributes = attrs.copy() as! UICollectionViewLayoutAttributes
            // 当 representedElementKind 是 nil 的时候，表示这个布局信息是 Item 的布局信息。
            if(attrs.representedElementKind == nil) {
            
                if attrs.indexPath.row % 2 == 0 {
                    newAttrs.frame.origin.x = 0
                    newAttrs.frame.origin.y = preY
                } else {
                    newAttrs.frame.origin.x = UIScreen.main.bounds.width/2;
                    newAttrs.frame.origin.y = preY
                }
                preY = newAttrs.frame.origin.y + newAttrs.frame.size.height - 10
            }
            newArray.add(newAttrs)
        }
        return (newArray as! [UICollectionViewLayoutAttributes])
    }
}
