//
//  ShareView.h
//  BrickMan
//
//  Created by TZ on 16/7/27.
//  Copyright © 2016年 BrickMan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMContent.h"

@interface ShareView : UIView
@property (strong, nonatomic) void(^successShareBlock)();

+ (ShareView *)showShareViewWithContent:(BMContent *)content;

@end

@interface ShareView_Item : UIView
@property (strong, nonatomic) NSString *snsName;
@property (copy, nonatomic) void(^clickedBlock)(NSString *snsName);
+ (instancetype)itemWithSnsName:(NSString *)snsName;

+ (CGFloat)itemWidth;
+ (CGFloat)itemHeight;
@end
