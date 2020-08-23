//
//  ViewController.swift
//  CollectionViewDemo
//
//  Created by 周素华 on 2020/8/16.
//  Copyright © 2020 周素华. All rights reserved.
//

import UIKit
 

class ViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
   
    var _collectionView : UICollectionView? = nil
    var _count = 16
    var _sectionCount = 1
    var titleArr = ["固县乡","中村镇","土沃乡","真村镇","固县乡","中村镇","土沃乡","真村镇","固县乡","中村镇","土沃乡","真村镇","固县乡","中村镇","土沃乡","真村镇"]
    var numArr = ["4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        let flowLayout = SimpleFlowLayout.init()
        _collectionView = UICollectionView.init(frame: CGRect(x: 0, y: 0, width: self.view.bounds.size.width, height: 400), collectionViewLayout: flowLayout)
        _collectionView?.delegate = self
        _collectionView?.dataSource = self
        _collectionView?.backgroundColor = UIColor.yellow
        self.view.addSubview(_collectionView!)
        
        // 注册cell
        _collectionView! .register(LeftCollectionViewCell.self, forCellWithReuseIdentifier:"leftCell")
         _collectionView! .register(RightCollectionViewCell.self, forCellWithReuseIdentifier:"rightCell")
//        _collectionView?.register(SimpleReusableView.self, forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader, withReuseIdentifier: "reusableView")
        
        let btn = UIButton.init(frame: CGRect(x: 100, y: 420, width: 100, height: 100))
        btn.addTarget(self, action: #selector(btnClick), for: UIControl.Event.touchUpInside)
        btn.backgroundColor = UIColor.red
        self.view.addSubview(btn)
    }
    
    @objc func btnClick() {
        
        _collectionView?.performBatchUpdates({
            
            _collectionView?.deleteItems(at: [IndexPath(row:0, section: 0)])
            _count -= 1;
            _collectionView?.insertItems(at: [IndexPath(row:1, section: 0)])
            _count += 1
        }, completion: nil)
    }
  
//    func numberOfSections(in collectionView: UICollectionView) -> Int {
//        _sectionCount
//    }
//
//    func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
//
//        let cell: SimpleReusableView = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: "reusableView", for: indexPath) as! SimpleReusableView
//        cell.num = String(indexPath.section)
//        cell.refreshData()
//        return cell
//    }
//
   func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
       
    _count
      }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        if(indexPath.row % 2 == 0) {
            let cell: LeftCollectionViewCell = collectionView.dequeueReusableCell(withReuseIdentifier: "leftCell", for: indexPath) as! LeftCollectionViewCell
            cell.titleLabel?.text = titleArr[indexPath.row]
            cell.numLabel?.text = numArr[indexPath.row]
            return cell
        } else {
            
            let cell: RightCollectionViewCell = collectionView.dequeueReusableCell(withReuseIdentifier: "rightCell", for: indexPath) as! RightCollectionViewCell
            cell.titleLabel?.text = titleArr[indexPath.row]
            cell.numLabel?.text = numArr[indexPath.row]
            return cell
        }
    }
       
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        
        print(indexPath.row, indexPath.section)
    }



}

