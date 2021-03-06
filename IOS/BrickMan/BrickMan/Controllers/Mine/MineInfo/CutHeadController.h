//
//  CutHeadController.h
//  BrickMan
//
//  Created by 段永瑞 on 16/7/22.
//  Copyright © 2016年 BrickMan. All rights reserved.
//

#import "BaseViewController.h"

@interface CutHeadController : BaseViewController

@property (nonatomic, strong) UIImage *image;
@property (copy, nonatomic) void(^updateBlock)(NSString *value);

@end
