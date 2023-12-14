
#import "Adjust_ext.h"

const int EVENT_OTHER_SOCIAL = 70;
extern int CreateDsMap( int _num, ... );
extern void CreateAsynEventWithDSMap(int dsmapindex, int event_index);
extern UIViewController *g_controller;
extern UIView *g_glView;
extern int g_DeviceWidth;
extern int g_DeviceHeight;

extern "C" const char* extOptGetString(char* _ext, char* _opt);
extern "C" double extOptGetReal(char* _ext, char* _opt);
extern "C" const char* extGetVersion(char* _ext);

#include <string>
extern std::string IniOptions_read(std::string extensionName, std::string key);

@implementation Adjust_ext

-(void) adjust_init
{
    NSString* yourAppToken = [NSString stringWithUTF8String: extOptGetString((char*)"Adjust", (char*)"iOS_AppToken")];
    
    NSString *environment = (extOptGetReal((char*)"Adjust", (char*)"Debug") > 0.5) ? ADJEnvironmentSandbox : ADJEnvironmentProduction;
    ADJConfig *adjustConfig = [ADJConfig configWithAppToken:yourAppToken environment:environment];
    
    /*
    [adjustConfig setLogLevel:ADJLogLevelVerbose];  // enable all logging
    [adjustConfig setLogLevel:ADJLogLevelDebug];    // enable more logging
    [adjustConfig setLogLevel:ADJLogLevelInfo];     // the default
    [adjustConfig setLogLevel:ADJLogLevelWarn];     // disable info logging
    [adjustConfig setLogLevel:ADJLogLevelError];    // disable warnings as well
    [adjustConfig setLogLevel:ADJLogLevelAssert];   // disable errors as well
    [adjustConfig setLogLevel:ADJLogLevelSuppress]; // disable all logging
    */
    
    [Adjust appDidLaunch:adjustConfig];
}


-(void) adjust_track_event: (NSString*) event jsonStr:(NSString*) jsonstr
{
    ADJEvent *mADJEvent = [ADJEvent eventWithEventToken:event];
    
    
    NSError *jsonError;
    NSData *objectData = [jsonstr dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:objectData
                                                            options:NSJSONReadingMutableContainers
                                                              error:&jsonError];
    
    
    if([dic objectForKey: @"Revenue"])
    {
        NSDictionary *myDict = [dic objectForKey: @"Revenue"];
        [mADJEvent setRevenue:[[myDict objectForKey: @"Amount"] doubleValue] currency:[myDict objectForKey: @"Currency"]];
    }

    if([dic objectForKey: @"OrderId"])
        [mADJEvent setTransactionId:[dic objectForKey:@"OrderId"]]; // avoid duplicates
    
    if([dic objectForKey: @"CallbackId"])
        [mADJEvent setCallbackId:[dic objectForKey:@"CallbackId"]]; // avoid duplicates
    
    
    if([dic objectForKey: @"CallbackParameter"])
    {
        NSDictionary *myDict = [dic objectForKey: @"CallbackParameter"];
        
        for(id key in myDict)
        {
            //NSLog(@"key=%@ value=%@", key, [myDict objectForKey:key]);
            [mADJEvent addCallbackParameter:key value:[myDict objectForKey:key]];
        }
    }
    
    if([dic objectForKey: @"PartnerParameter"])
    {
        NSDictionary *myDict = [dic objectForKey: @"PartnerParameter"];
        
        for(id key in myDict)
        {
            //NSLog(@"key=%@ value=%@", key, [myDict objectForKey:key]);
            [mADJEvent addPartnerParameter:key value:[myDict objectForKey:key]];
        }
    }
    
    [Adjust trackEvent:mADJEvent];
}


-(void) adjust_add_session_callback_parameter:(NSString*)key value:(NSString*) value
{
    [Adjust addSessionCallbackParameter:key value:value];
}

-(void) adjust_remove_session_callback_parameter:(NSString*)key
{
    [Adjust removeSessionCallbackParameter:key];
}

-(void) adjust_reset_session_callback_parameters
{
    [Adjust resetSessionCallbackParameters];
}

-(void) adjust_add_session_partner_parameter:(NSString*)key value:(NSString*) value
{
    [Adjust addSessionPartnerParameter:key value:value];
}

-(void) adjust_remove_session_partner_parameter:(NSString*)key
{
    [Adjust removeSessionPartnerParameter:key];
}

-(void) adjust_reset_session_partner_parameters
{
    [Adjust resetSessionPartnerParameters];
}


// -(void) Adjust_SetCoppaCompliantEnabled:(double) value
// {
    // [adjustConfig setCoppaCompliantEnabled:value >= 0.5];
// }


@end

