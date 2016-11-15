//
//  NSObject+Common.h
//  BrickMan
//
//  Created by TZ on 16/7/19.
//  Copyright © 2016年 BrickMan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BMUser.h"

@interface NSObject (Common)

#pragma mark - error 
+ (BOOL)showError:(NSError *)error;
+ (void)showHudTipStr:(NSString *)tipStr;
+ (void)showSuccessMsg:(NSString *)success;
+ (void)showErrorMsg:(NSString *)error;
+ (instancetype)showHUDQueryStr:(NSString *)titleStr;
+ (NSUInteger)hideHUDQuery;
- (id)handleResponse:(id)responseJSON autoShowError:(BOOL)autoShowError;

#pragma mark - 缓存
+ (void)saveResponseData:(NSDictionary *)data toPath:(NSString *)requestPath;
+ (id)loadResponseWithPath:(NSString *)requestPath;

+ (NSString* )pathInCacheDirectory:(NSString *)fileName;
+ (BOOL) createDirInCache:(NSString *)dirName;

@end
