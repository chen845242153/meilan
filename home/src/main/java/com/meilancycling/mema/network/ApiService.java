package com.meilancycling.mema.network;

import com.meilancycling.mema.network.bean.request.AddCommentRequest;
import com.meilancycling.mema.network.bean.request.AddFeedbackRequest;
import com.meilancycling.mema.network.bean.request.AddSensorRequest;
import com.meilancycling.mema.network.bean.request.AppUpdateRequest;
import com.meilancycling.mema.network.bean.request.AuthorRequest;
import com.meilancycling.mema.network.bean.request.CheckPhoneOrMailRequest;
import com.meilancycling.mema.network.bean.request.ClubSearchRequest;
import com.meilancycling.mema.network.bean.request.CommentRequest;
import com.meilancycling.mema.network.bean.request.CountryInfoRequest;
import com.meilancycling.mema.network.bean.request.DeleteMotionRequest;
import com.meilancycling.mema.network.bean.request.DeviceUpdateRequest;
import com.meilancycling.mema.network.bean.request.EditUserInformation;
import com.meilancycling.mema.network.bean.request.Encryption;
import com.meilancycling.mema.network.bean.request.GetCodeRequest;
import com.meilancycling.mema.network.bean.request.MobileLogin;
import com.meilancycling.mema.network.bean.request.MotionInfoRequest;
import com.meilancycling.mema.network.bean.request.MotionRequest;
import com.meilancycling.mema.network.bean.request.NewsInfoRequest;
import com.meilancycling.mema.network.bean.request.NewsListRequest;
import com.meilancycling.mema.network.bean.request.PhoneRegisterRequest;
import com.meilancycling.mema.network.bean.request.QueryMotionInfoRequest;
import com.meilancycling.mema.network.bean.request.QueryWebUrlRequest;
import com.meilancycling.mema.network.bean.request.RankingRequest;
import com.meilancycling.mema.network.bean.request.RegistryActivityRequest;
import com.meilancycling.mema.network.bean.request.SelectRegRequest;
import com.meilancycling.mema.network.bean.request.SessionRequest;
import com.meilancycling.mema.network.bean.request.ThirPartyLoginRequest;
import com.meilancycling.mema.network.bean.request.UpMotionInfoRequest;
import com.meilancycling.mema.network.bean.request.UpdateMotionRequest;
import com.meilancycling.mema.network.bean.request.UpdateRegRequest;
import com.meilancycling.mema.network.bean.request.UserBindingRequest;
import com.meilancycling.mema.network.bean.request.VersionInsetRequest;
import com.meilancycling.mema.network.bean.response.ActivityDetailsResponse;
import com.meilancycling.mema.network.bean.response.ActivityListResponse;
import com.meilancycling.mema.network.bean.response.ActivityRankingResponse;
import com.meilancycling.mema.network.bean.response.AppUpdateResponse;
import com.meilancycling.mema.network.bean.response.AuthorListResponse;
import com.meilancycling.mema.network.bean.response.ClubSearchResponse;
import com.meilancycling.mema.network.bean.response.CommentResponse;
import com.meilancycling.mema.network.bean.response.CommonData;
import com.meilancycling.mema.network.bean.response.CommonProblemResponse;
import com.meilancycling.mema.network.bean.response.CountryInfoResponse;
import com.meilancycling.mema.network.bean.response.DeviceUpdateResponse;
import com.meilancycling.mema.network.bean.response.GetKeyBean;
import com.meilancycling.mema.network.bean.response.LoginBean;
import com.meilancycling.mema.network.bean.response.MostMotionResponse;
import com.meilancycling.mema.network.bean.response.MotionDetailsResponse;
import com.meilancycling.mema.network.bean.response.MotionInfoResponse;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.network.bean.response.NewsInfoResponse;
import com.meilancycling.mema.network.bean.response.NewsListResponse;
import com.meilancycling.mema.network.bean.response.NewsRecommendResponse;
import com.meilancycling.mema.network.bean.response.RankingResponse;
import com.meilancycling.mema.network.bean.response.ResetPasswordRequest;
import com.meilancycling.mema.network.bean.response.SelectRegResponse;
import com.meilancycling.mema.network.bean.response.SessionResponse;
import com.meilancycling.mema.network.bean.response.SportsRemarkResponse;
import com.meilancycling.mema.network.bean.response.UserHeadResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author lion
 * 网络请求接口
 */
public interface ApiService {
    /**
     * 文件下载
     *
     * @param url 请求对象
     * @return 结果
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    /**
     * 常见问题的list
     *
     * @param url 请求对象
     * @return 结果
     */
    @GET
    Observable<BaseResponse<List<CommonProblemResponse>>> queryContentList(@Url String url);

    /**
     * 基础数据
     */
    @GET
    Observable<BaseResponse<CommonData>> getCommonData(@Url String url);

    /**
     * 运动数据汇总
     *
     * @param motionRequest 请求对象
     * @return 结果
     */
    @POST("password/motion/data/v201/queryMotionSumData")
    Observable<BaseResponse<MotionSumResponse>> queryMotionSumData(@Body MotionRequest motionRequest);

    /**
     * 根据条件(运动类型，竞赛数据)类型获取运动数据list
     *
     * @param motionInfoRequest 请求对象
     * @return 结果
     */
    @POST("password/motion/data/v201/listMotionInfo")
    Observable<BaseResponse<MotionInfoResponse>> listMotionInfo(@Body MotionInfoRequest motionInfoRequest);

    /**
     * 查询运动备注信息
     */
    @POST("password/motion/data/v201/selectMotionRemarks")
    Observable<BaseResponse<SportsRemarkResponse>> selectMotionRemarks(@Body QueryMotionInfoRequest queryMotionInfoRequest);

    /**
     * 修改运动数据名称与记录
     *
     * @param updateMotionRequest 请求对象
     * @return 结果
     */
    @POST("password/motion/info/v201/updateMotion")
    Observable<BaseResponse<Object>> updateMotion(@Body UpdateMotionRequest updateMotionRequest);

    /**
     * 查询一条运动详细数据（基本数据与详细数据共用）
     *
     * @param queryMotionInfoRequest 请求对象
     * @return 结果
     */
    @POST("password/motion/data/v201/queryMotionInfo")
    Observable<BaseResponse<MotionDetailsResponse>> queryMotionInfo(@Body QueryMotionInfoRequest queryMotionInfoRequest);

    /**
     * 设置为竞赛数据或者取消
     *
     * @param upMotionInfoRequest 请求对象
     * @return 结果
     */
    @POST("password/motion/info/v201/upCompetition")
    Observable<BaseResponse<Object>> upCompetition(@Body UpMotionInfoRequest upMotionInfoRequest);

    /**
     * 设置为竞赛数据或者取消
     *
     * @param deleteMotionRequest 请求对象
     * @return 结果
     */
    @POST("password/motion/info/v201/deleteByMotionInfo")
    Observable<BaseResponse<Object>> deleteByMotionInfo(@Body DeleteMotionRequest deleteMotionRequest);

    /**
     * 个人数据汇总汇总并且返回最远距离与最长用时
     *
     * @param sessionRequest 请求对象
     * @return 结果
     */
    @POST("password/motion/data/v201/queryUserMotionSum")
    Observable<BaseResponse<MostMotionResponse>> queryUserMotionSum(@Body SessionRequest sessionRequest);

    /**
     * session获取用户缓存信息
     *
     * @param sessionRequest 请求对象
     *                       * @return 结果
     */
    @POST("password/user/data/v101/getinfobysession")
    Observable<BaseResponse<SessionResponse>> getInfoBySession(@Body SessionRequest sessionRequest);

    /**
     * 上传图片
     *
     * @param map   请求对象
     * @param files
     * @return 结果
     */
    @Multipart
    @POST("password/user/data/v101/updateheaderphoto")
    Observable<BaseResponse<UserHeadResponse>> uploadPic(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part files);

    /**
     * 反馈意见信息
     *
     * @param addFeedbackRequest 请求对象
     * @return 结果
     */
    @POST("password/feedback/v201/setUserFeedback")
    Observable<BaseResponse<Object>> addFeedback(@Body AddFeedbackRequest addFeedbackRequest);

    /**
     * 查询协议（注销协议，登录协议）
     *
     * @param queryWebUrlRequest 请求对象
     * @return 结果
     */
    @POST("password/contentInfo/v201/queryContentInfoByType")
    Observable<BaseResponse<CommonProblemResponse>> queryContentInfoByType(@Body QueryWebUrlRequest queryWebUrlRequest);

    /**
     * 获取RSA公钥+私钥缓存KEY
     *
     * @param url 请求对象
     * @return 结果
     */
    @GET
    Observable<BaseResponse<GetKeyBean>> getKey(@Url String url);

    /**
     * 加密
     *
     * @param encryption 请求对象
     * @return 结果
     */
    @POST("password/security/encryption")
    Observable<BaseResponse<String>> encryption(@Body Encryption encryption);

    /**
     * 用户登陆
     *
     * @param mobileLogin 请求对象
     * @return 结果
     */
    @POST("password/sso/v101/mobilelogin")
    Observable<BaseResponse<LoginBean>> mobileLogin(@Body MobileLogin mobileLogin);

    /**
     * 第三方注册与登录
     *
     * @param thirPartyLoginRequest 请求对象
     * @return 结果
     */
    @POST("password/sso/v101/thirParty/login")
    Observable<BaseResponse<LoginBean>> threePartyLogin(@Body ThirPartyLoginRequest thirPartyLoginRequest);

    /**
     * 用户注销
     *
     * @param sessionRequest 请求对象
     * @return 结果
     */
    @POST("password/reg/v101/cancellation")
    Observable<BaseResponse<Object>> cancellation(@Body SessionRequest sessionRequest);

    /**
     * 查询手机号与邮箱是否已注册
     *
     * @param checkPhoneOrMailRequest 请求对象
     * @return 结果
     */
    @POST("password/reg/v101/getByPhoneMail")
    Observable<BaseResponse<String>> checkPhoneOrMail(@Body CheckPhoneOrMailRequest checkPhoneOrMailRequest);


    /**
     * 发送验证码
     *
     * @param getCodeRequest 请求对象  mesType 1,注册校验码;2,修改密码操作校验码;3,登陆校验码
     * @return 结果
     */
    @POST("password/mes/v101/sendMes")
    Observable<BaseResponse<Object>> getVerificationCode(@Body GetCodeRequest getCodeRequest);

    /**
     * 重置登录密码
     *
     * @param resetPasswordRequest 请求对象
     * @return 结果
     */
    @POST("password/reg/v101/resetPassword")
    Observable<BaseResponse<Object>> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    /**
     * 手机邮箱注册
     *
     * @param phoneRegisterRequest 请求对象
     * @return 结果
     */
    @POST("password/reg/v101/mobile")
    Observable<BaseResponse<LoginBean>> phoneOrMailRegister(@Body PhoneRegisterRequest phoneRegisterRequest);

    /**
     * 取消一条授权
     *
     * @param authorRequest 请求对象
     * @return 结果
     */
    @POST("password/authorize/info/v201/cancelAuthorize")
    Observable<BaseResponse<Object>> cancelAuthorize(@Body AuthorRequest authorRequest);

    /**
     * 添加一条授权
     *
     * @param authorRequest 请求对象
     * @return 结果
     */
    @POST("password/authorize/info/v201/addAuthorize")
    Observable<BaseResponse<Object>> addAuthorize(@Body AuthorRequest authorRequest);

    /**
     * 获取应用列表
     *
     * @param sessionRequest 请求对象
     * @return 结果
     */
    @POST("password/authorize/info/v201/list")
    Observable<BaseResponse<List<AuthorListResponse>>> authList(@Body SessionRequest sessionRequest);

    /**
     * 修改一条授权信息
     *
     * @param authorRequest 请求对象
     * @return
     */
    @POST("password/authorize/info/v201/updateAuthorize")
    Observable<BaseResponse<Object>> updateAuthorize(@Body AuthorRequest authorRequest);

    /**
     * 上传文件
     *
     * @param map   请求对象
     * @param files
     * @return 结果
     */
    @Multipart
    @POST("password/motion/info/v201/uploadFitFile")
    Observable<BaseResponse<String>> uploadFitFile(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part files);

    /**
     * 获取硬件升级信息
     *
     * @param versionUpdateRequest 请求对象
     * @return 结果
     */
    @POST("password/hardware/v101/version/get")
    Observable<BaseResponse<DeviceUpdateResponse>> deviceUpdate(@Body DeviceUpdateRequest versionUpdateRequest);

    /**
     * 插入硬件升级信息
     *
     * @param versionInsetRequest 请求对象
     * @return 结果
     */
    @POST("password/hardware/upgrade/v101/insert")
    Observable<BaseResponse<Object>> versionUpdateInset(@Body VersionInsetRequest versionInsetRequest);

    /**
     * 获取用户升级列表
     */
    @POST("password/hardware/upgrade/v101/list")
    Observable<BaseResponse<List<VersionInsetRequest>>> upgradeList(@Body SessionRequest sessionRequest);

    /**
     * 修改升级状态
     *
     * @param versionInsetRequest 请求对象
     * @return 结果
     */
    @POST("password/hardware/upgrade/v101/update")
    Observable<BaseResponse<Object>> versionUpdateUpdate(@Body VersionInsetRequest versionInsetRequest);

    /**
     * app版本升级
     *
     * @param appUpdateRequest 请求对象
     * @return 结果
     */
    @POST("password/version/update")
    Observable<BaseResponse<AppUpdateResponse>> appUpdate(@Body AppUpdateRequest appUpdateRequest);

    /**
     * 编辑用户详情
     *
     * @param editUserInformation 请求对象
     * @return 结果
     */
    @POST("password/user/data/v101/edit")
    Observable<BaseResponse<Object>> editUserInformation(@Body EditUserInformation editUserInformation);

    /**
     * 用户绑定邮箱号码
     *
     * @param userBindingRequest 请求对象
     * @return 结果
     */
    @POST("password/userActions/v301/userBindingAccount")
    Observable<BaseResponse<Object>> userBindingAccount(@Body UserBindingRequest userBindingRequest);

    /**
     * 用户解除邮箱号码
     *
     * @param userBindingRequest 请求对象
     * @return 结果
     */
    @POST("password/userActions/v301/userUnbindmailbox")
    Observable<BaseResponse<Object>> userUnbindmailbox(@Body UserBindingRequest userBindingRequest);

    /**
     * 获取国家列表
     *
     * @param countryInfoRequest 请求对象
     * @return 国家列表
     */
    @POST("password/country/info/v101/list")
    Observable<BaseResponse<CountryInfoResponse>> countryInfo(@Body CountryInfoRequest countryInfoRequest);

    /**
     * 用户添加传感器
     */
    @POST("password/product/v301/setSensorList")
    Observable<BaseResponse<Object>> addSensorList(@Body AddSensorRequest addSensorRequest);


    /**
     * 新闻推荐和活动列表
     *
     * @param sessionRequest 请求对象
     * @return 结果
     */
    @POST("interactive/news/v302/recommend")
    Observable<BaseResponse<List<NewsRecommendResponse>>> recommend(@Body SessionRequest sessionRequest);

    /**
     * 获取新闻列表
     *
     * @param newsListRequest 请求对象
     * @return 结果
     */
    @POST("interactive/news/v302/list")
    Observable<BaseResponse<NewsListResponse>> newsList(@Body NewsListRequest newsListRequest);

    /**
     * 获取新闻详细数据
     *
     * @param newsInfoRequest 请求对象
     * @return 结果
     */
    @POST("interactive/news/v302/selectNewsInfo")
    Observable<BaseResponse<NewsInfoResponse>> selectNewsInfo(@Body NewsInfoRequest newsInfoRequest);

    /**
     * 新闻点赞
     *
     * @param newsInfoRequest 请求对象
     * @return 结果
     */
    @POST("interactive/news/v302/addNewsLikes")
    Observable<BaseResponse<Object>> addNewsLikes(@Body NewsInfoRequest newsInfoRequest);

    /**
     * 新闻取消点赞
     *
     * @param newsInfoRequest 请求对象
     * @return 结果
     */
    @POST("interactive/news/v302/dleNewsLikes")
    Observable<BaseResponse<Object>> dleNewsLikes(@Body NewsInfoRequest newsInfoRequest);

    /**
     * 获取活动列表
     *
     * @param newsListRequest 请求对象
     * @return 结果
     */
    @POST("interactive/activity/v302/list")
    Observable<BaseResponse<ActivityListResponse>> activityList(@Body NewsListRequest newsListRequest);

    /**
     * 评论列表
     *
     * @param commentRequest 请求对象
     * @return 结果
     */
    @POST("interactive/comment/v302/list")
    Observable<BaseResponse<CommentResponse>> commentList(@Body CommentRequest commentRequest);

    /**
     * 添加评论
     *
     * @param addCommentRequest 请求对象
     * @return 结果
     */
    @POST("interactive/comment/v302/add")
    Observable<BaseResponse<Object>> addComment(@Body AddCommentRequest addCommentRequest);

    /**
     * 搜索新闻与活动列表
     *
     * @param clubSearchRequest 请求对象
     * @return 结果
     */
    @POST("interactive/news/v302/search")
    Observable<BaseResponse<ClubSearchResponse>> clubSearch(@Body ClubSearchRequest clubSearchRequest);

    /**
     * 获取活动详情
     *
     * @param newsInfoRequest 请求对象
     * @return 结果
     */
    @POST("interactive/activity/v302/queryActivity")
    Observable<BaseResponse<ActivityDetailsResponse>> queryActivity(@Body NewsInfoRequest newsInfoRequest);

    /**
     * 活动点赞
     *
     * @param newsInfoRequest 请求对象
     * @return 结果
     */
    @POST("interactive/activity/v302/addActivityLikes")
    Observable<BaseResponse<Object>> addActivityLikes(@Body NewsInfoRequest newsInfoRequest);

    /**
     * 活动取消点赞
     *
     * @param newsInfoRequest 请求对象
     * @return 结果
     */
    @POST("interactive/activity/v302/dleActivityLikes")
    Observable<BaseResponse<Object>> dleActivityLikes(@Body NewsInfoRequest newsInfoRequest);

    /**
     * 参加活动
     *
     * @param newsInfoRequest 请求对象
     * @return 结果
     */
    @POST("interactive/activity/v302/registryActivity")
    Observable<BaseResponse<Object>> registryActivity(@Body RegistryActivityRequest newsInfoRequest);

    /**
     * 取消报名
     *
     * @param registryActivityRequest 请求对象
     * @return 结果
     */
    @POST("interactive/activity/v302/dleRegActivity")
    Observable<BaseResponse<Object>> dleRegActivity(@Body RegistryActivityRequest registryActivityRequest);

    /**
     * 活动排名
     *
     * @param newsInfoRequest 请求对象
     * @return 结果
     */
    @POST("interactive/activity/v302/activityRanking")
    Observable<BaseResponse<ActivityRankingResponse>> activityRanking(@Body NewsInfoRequest newsInfoRequest);

    /**
     * 查询报名信息
     *
     * @param selectRegRequest 请求对象
     * @return 结果
     */
    @POST("interactive/activity/v302/selectRegActivity")
    Observable<BaseResponse<SelectRegResponse>> selectRegActivity(@Body SelectRegRequest selectRegRequest);

    /**
     * 修改报名信息
     *
     * @param updateRegRequest 请求对象
     * @return 结果
     */
    @POST("interactive/activity/v302/updateRegActivity")
    Observable<BaseResponse<Object>> updateRegActivity(@Body UpdateRegRequest updateRegRequest);

    /**
     * 运动数据排名（月，年，总）
     *
     * @param rankingRequest 请求对象
     * @return 结果
     */
    @POST("password/motion/ranking/v302/get")
    Observable<BaseResponse<RankingResponse>> getRankingList(@Body RankingRequest rankingRequest);
}
