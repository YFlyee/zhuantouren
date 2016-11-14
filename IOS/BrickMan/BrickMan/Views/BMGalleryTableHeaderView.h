//
//  BMGalleryTableHeaderView.h
//  BrickMan
//
//  Created by ys on 16/11/11.
//  Copyright © 2016年 BrickMan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BMGalleryTableHeaderView : UIView

@property (nonatomic, copy) void (^popGalleryBlock)();

- (void)configHeaderViewWithUser:(BMUser *)user;
- (void)configItemsWith:(CGFloat)offset;
@end
