<!-- 红色简洁登录页面 -->
<template>
	<view class="login-root">
		<image class="img-a" src="@/static/login-common/bg1.png"></image>
		<view class="login-content">
			<view class="t-b">登 录</view>
			<form class="login-form">
				<view class="login-item">
					<image src="@/static/login-common/user.png"></image>
					<input type="number" name="phone" placeholder="请输入手机号码" maxlength="11"
						v-model="userInfo.phoneNumber" />
				</view>
				<view class="login-item">
					<image src="@/static/login-common/pwd.png"></image>
					<input type="password" name="code" maxlength="6" placeholder="请输入密码" v-model="userInfo.password" />
				</view>
				<view class="login-forgotpwd" @tap="forgotPwd()">忘记密码</view>
				<button @click="login()">登 录</button>
			</form>
		</view>
		<view class="login-item-cardBox">
			<view>
				还没有登录账号？
				<text class="txt" @tap="reg()">立刻注册</text>
			</view>
		</view>
		<image class="img-b" src="@/static/login-common/bg2.png"></image>
	</view>
</template>
<script>
	export default {

		data() {
			return {
				userInfo: {
					phoneNumber: '18070625716', //手机号码
					password: '123456', //密码
					userName: '',
				}
			};
		},
		onLoad() {},
		methods: {
			//当前登录按钮操作
			login() {
				console.log("正在登录")
				var that = this;
				if (!that.userInfo.phoneNumber) {
					uni.showToast({
						title: '请输入手机号',
						icon: 'none'
					});
					return;
				}
				if (!/^[1][3,4,5,7,8,9][0-9]{9}$/.test(that.userInfo.phoneNumber)) {
					uni.showToast({
						title: '请输入正确手机号',
						icon: 'none'
					});
					return;
				}
				if (!that.userInfo.password) {
					uni.showToast({
						title: '请输入密码',
						icon: 'none'
					});
					return;
				}
				that.userInfo.userName = that.userInfo.phoneNumber
			
				uni.request({
					
					url: getApp().globalData.mainHost + '/login/login',
					method: "POST",
					data: {
						"userInfo": that.userInfo
					},
					success: (res) => {
						console.log(JSON.stringify(res))
						if (res.statusCode !== 200) {
							uni.showToast({
								title: "服务器内部错误",
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
					},
					fail: (res) => {
						console.log(JSON.stringify(res))
					}
				})
				// uni.request({
				// 	method: "POST",
				// 	data: {
				// 		userInfo:this.userInfo,
				// 		code:this.code
						
				// 	},
					
				// 	url: getApp().globalData.mainHost '/login/login',
				// 		dataType: "json",
				// 	success: (res) => {
				// 	console.log(JSON.stringify(res))
				// 		},
					
				// 	fail: (res) => {
				// 		console.log("失败")
				// 	}
				// })
			},
			//忘记密码
			forgotPwd() {
				// uni.showToast({
				// 	title: '忘记密码',
				// 	icon: 'none'
				// });
				uni.redirectTo({
					url: '../reset-pwd/reset-pwd'
				})
			},
			//立刻注册
			reg() {
				// uni.showToast({
				// 	title: '立刻注册',
				// 	icon: 'none'
				// });
				uni.redirectTo({
					url: "../register/register"
				})
			}
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

	.login-root {
		height: 100vh;
		background: linear-gradient(to bottom, #ff6a9a, #fe7d76);
	}

	.login-content {
		/* width: 580rpx;
		 */
		width: 86%;
		padding: 55rpx;
		margin: 0 auto;
		font-size: 28rpx;
		background-color: #ffffff;
		border-radius: 20rpx;
		position: relative;
		margin-top: -200rpx;
		box-shadow: 0 5px 7px 0 rgba(0, 0, 0, 0.15);
		z-index: 9;
	}

	.login-content button {
		font-size: 28rpx;
		background: linear-gradient(to right, #ff8f77, #fe519f);
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 50rpx;
	}

	.login-content input {
		padding: 0 10rpx 0 120rpx;
		height: 90rpx;
		line-height: 90rpx;
		margin-bottom: 50rpx;
		background: #f6f6f6;
		border: 1px solid #f6f6f6;
		font-size: 28rpx;
		border-radius: 50rpx;
	}

	.login-content .login-item {
		position: relative;
	}

	.login-content .login-item image {
		width: 50rpx;
		height: 50rpx;
		position: absolute;
		left: 40rpx;
		top: 28rpx;
		padding-right: 20rpx;
	}

	.login-content .t-b {
		text-align: left;
		font-size: 46rpx;
		color: #ff939b;
		font-weight: bold;
		margin: 0 0 50rpx 20rpx;
	}

	.login-content .t-d {
		text-align: center;
		color: #999;
		margin: 80rpx 0;
	}

	.login-content .login-forgotpwd {
		text-align: right;
		color: #c0c0c0;
		margin: -20rpx 30rpx 40rpx 0;
	}

	.login-content .t-f {
		text-align: center;
		margin: 200rpx 0 0 0;
		color: #666;
	}

	.login-content .t-f text {
		margin-left: 20rpx;
		color: #aaaaaa;
		font-size: 27rpx;
	}

	.login-content .uni-input-placeholder {
		color: #aeaeae;
	}

	.login-form {
		zoom: 1;
	}

	.login-form:after {
		clear: both;
		display: block;
		visibility: hidden;
		height: 0;
		content: '\20';
	}

	.login-item-cardBox {
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

	.login-item-cardBox .txt {
		color: #fe519f;
	}
</style>
