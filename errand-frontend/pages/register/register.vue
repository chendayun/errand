<template>
	<view class="login-bg">
		<image class="img-a" src="@/static/login-common/bg1.png"></image>
		<view class="t-login">
			<view class="t-b">注 册</view>
			<form class="cl">
				<view class="t-a">
					<image src="@/static/login-common/user.png"></image>
					<input type="number" name="phone" placeholder="请输入手机号码" maxlength="11" v-model="phone" />
				</view>
				<view class="t-a">
					<image src="@/static/login-common/pwd.png"></image>
					<input type="password" name="code" maxlength="6" placeholder="请输入密码" v-model="pwd" />
				</view>
				<view class="t-a">
					<image src="@/static/login-common/pwd.png"></image>
					<input type="password" name="code" maxlength="6" placeholder="请确认密码" v-model="newpwd" />
				</view>
				<view class="t-a">
					<image src="@/static/login-common/yzm.png"></image>
					<input type="number" name="code" maxlength="6" placeholder="请输入验证码" v-model="yzm" />
					<view v-if="showText" class="t-c-2" @click="getCode()">发送短信</view>
					<view v-else class="t-c-2" style="background-color: #A7A7A7;">重新发送({{ second }})</view>
				</view>
				<view class="t-c" @tap="forgotPwd()">忘记密码</view>
				<button @click="register()">注册</button>
			</form>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				phone: '18070655716', //手机号码
				pwd: '123456', //密码,
				newpwd: '123456',
				showText: true, //判断短信是否发送
				userInfo:{},
				yzm: '', //验证码
				second: 60

			};
		},
		onLoad() {},
		methods: {
			//当前注册按钮操作
			register() {
				var that = this;
				let userInfo ={};
				if (!that.phone) {
					uni.showToast({
						title: '请输入手机号',
						icon: 'none'
					});
					return;
				}
				if (!/^[1][3,4,5,7,8,9][0-9]{9}$/.test(that.phone)) {
					uni.showToast({
						title: '请输入正确手机号',
						icon: 'none'
					});
					return;
				}
				if (!that.pwd) {
					uni.showToast({
						title: '请输入密码',
						icon: 'none'
					});
					return;
				}
				if (!that.newpwd) {
					uni.showToast({
						title: '请输入确认密码',
						icon: 'none'
					});
					return;
				}
				if (that.pwd != that.newpwd) {
					uni.showToast({
						title: '两次密码不一致',
						icon: 'none'
					});
					return;
				}
				that.userInfo.userName  = that.phone;
				that.userInfo.phoneNumber=that.phone;
				that.userInfo.password=that.pwd;
				uni.request({
					url:getApp().globalData.mainHost +"/register/register",
					method:"POST",
					data:{
						userInfo:that.userInfo
					},
					success: (res) => {
						console.log(JSON.stringify(res))
						if (res.statusCode !== 200) {
							uni.showToast({
								title: "服务器内部错误",
								icon:"none",
								duration: 500,
							});
							return;
						}
						if (res.data.retCode != 0) {
							uni.showToast({
								title:  res.data.retDesc,
								icon:"none",
								duration: 2000,
							});
							
							return;
						}
					}
				})
			},
			//忘记密码
			forgotPwd() {
				uni.redirectTo({
					url: '../reset-pwd/reset-pwd'
				})
			},

			//获取短信验证码
			getCode() {
				var that = this;
				var interval = setInterval(() => {
					that.showText = false;
					var times = that.second - 1;
					//that.second = times<10?'0'+times:times ;//小于10秒补 0
					that.second = times;
					console.log(times);
				}, 1000);
				setTimeout(() => {
					clearInterval(interval);
					that.second = 60;
					that.showText = true;
				}, 60000);
				//这里请求后台获取短信验证码
				uni.request({
					//......//此处省略
					success: function(res) {
						that.showText = false;
					}
				});
			},
		}
	};
</script>
<style>
	.img-a {
		width: 100%;
	}

	.img-b {
		width: 100%;
		height: 45px;
		bottom: 0;
		position: absolute;
	}

	.login-bg {
		height: 100vh;
		background: linear-gradient(to bottom, #ff6a9a, #fe7d76);
	}

	.t-login {
		width: 580rpx;
		padding: 55rpx;
		margin: 0 auto;
		font-size: 28rpx;
		background-color: #ffffff;
		border-radius: 20rpx;
		position: relative;
		margin-top: -300rpx;
		box-shadow: 0 5px 7px 0 rgba(0, 0, 0, 0.15);
		z-index: 9;
	}

	.t-login button {
		font-size: 28rpx;
		background: linear-gradient(to right, #ff8f77, #fe519f);
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 50rpx;
	}

	.t-login input {
		padding: 0 20rpx 0 120rpx;
		height: 90rpx;
		line-height: 90rpx;
		margin-bottom: 50rpx;
		background: #f6f6f6;
		border: 1px solid #f6f6f6;
		font-size: 28rpx;
		border-radius: 50rpx;
	}

	.t-login .t-a {
		position: relative;
	}

	.t-login .t-a image {
		width: 40rpx;
		height: 40rpx;
		position: absolute;
		left: 40rpx;
		top: 28rpx;
		padding-right: 20rpx;
	}

	.t-login .t-b {
		text-align: left;
		font-size: 46rpx;
		color: #ff939b;
		font-weight: bold;
		margin: 0 0 50rpx 20rpx;
	}

	.t-login .t-d {
		text-align: center;
		color: #999;
		margin: 80rpx 0;
	}

	.t-login .t-c {
		text-align: right;
		color: #c0c0c0;
		margin: -20rpx 30rpx 40rpx 0;
	}

	.t-login .t-c-2 {
		position: absolute;
		right: 22rpx;
		top: 22rpx;
		background: #5677fc;
		color: #fff;
		font-size: 24rpx;
		border-radius: 50rpx;
		height: 50rpx;
		line-height: 50rpx;
		padding: 0 25rpx;
	}

	.t-login .t-f {
		text-align: center;
		margin: 200rpx 0 0 0;
		color: #666;
	}

	.t-login .t-f text {
		margin-left: 20rpx;
		color: #aaaaaa;
		font-size: 27rpx;
	}

	.t-login .uni-input-placeholder {
		color: #aeaeae;
	}

	.cl {
		zoom: 1;
	}

	.cl:after {
		clear: both;
		display: block;
		visibility: hidden;
		height: 0;
		content: '\20';
	}

	.cardBox {
		-webkit-box-orient: horizontal;
		-webkit-box-direction: normal;
		-webkit-flex-direction: row;
		flex-direction: row;
		-webkit-box-align: center;
		-webkit-align-items: center;
		align-items: center;
		padding: 5px;
		background: #ffffff;
		opacity: 0.9;
		-webkit-border-radius: 20rpx;
		border-radius: 0 0 20rpx 20rpx;
		height: 70rpx;
		width: 600rpx;
		margin: 0 auto;
		position: relative;
		text-align: center;
		line-height: 70rpx;
		color: #aaa;
		font-size: 28rpx;
	}

	.cardBox .txt {
		color: #fe519f;
	}
</style>
