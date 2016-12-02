//
//  BMComment.h
//  BrickMan
//
//  Created by TZ on 16/8/25.
//  Copyright © 2016年 BrickMan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BMUser.h"

@interface BMComment : NSObject

@property (strong, nonatomic) NSNumber *id, *contentId, *updateId, *createdTime;
@property (strong, nonatomic) NSString *commentContent, *updateTime, *userId;
@property (strong, nonatomic) NSDate *date;
@property (strong, nonatomic) BMUser *user;

@end
