<template>
	<view class="fill">
		<view class="bg-white margin-bottom-sm">
			<view class="cu-form-group ">
				<view class="title">姓<span class="padding-left padding-right"></span>名</view>
				<input placeholder="请输入" class="input-class" v-model="form.courierName"></input>
			</view>
			<view class="cu-form-group ">
				<view class="title">性<span class="padding-left padding-right"></span>别</view>
				<view class="radio-group">
					<view class="radio" :class="{'checked': form.sex == 'man'}" style="margin-right: 10rpx;"
						@tap="form.sex='man'">男生</view>
					<view class="radio" :class="{'checked': form.sex == 'woMan'}" @tap="form.sex='woMan'">女生</view>
				</view>
			</view>
			<view class="cu-form-group ">
				<view class="title">联系方式</view>
				<input placeholder="请输入" class="input-class" v-model="form.courierPhone"></input>
			</view>
			<view class="cu-form-group " @tap="chooseSchool">
				<view class="title">申请学校</view>
				<view>
					<input placeholder="请选择" class="input-class" v-model="form.schoolName" disabled="disabled"></input>
				</view>
			</view>
		</view>
		<view class="pic bg-white padding">
			<view class="pic_01" @tap="uploadFront()">
				<img v-if="!form.idImgFront" src="/static/fill_icon/id_front.png"></img>
				<img v-else :src="form.idImgFront"></img>
				请上传身份证正面
			</view>
			<view class="pic_01" @tap="uploadBack()">
				<img v-if="!form.idImgBack" src="/static/fill/id_back.png"></img>
				<img v-else :src="form.idImgBack"></img>
				请上传身份证反面
			</view>
			<view class="pic_01" @tap="uploadIdCard()">
				<img v-if="!form.perImgSelf" src="/static/fill/courier_user.png"></img>
				<img v-else :src="form.perImgSelf"></img>
				请上传个人自拍照
			</view>
		</view>

		<view style="background: #fff;">
			<view style="padding: 20px;">
				<button type="primary" size="default" @tap="submit">提交</button>
			</view>

		</view>


	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					schoolId: '',
					schoolName: '',
					sex: 'man',
					courierName: '',
					courierPhone: '',
					idImgFront: '',
					idImgBack: '',
					perImgSelf: '',
					lat: '',
					lon: ''
				}
			};
		},
		onLoad(option) {
			console.log(option)
			if (option.status === 'modify') {
				//获取已经修改过的信息
				var self = this
				this.iGlobal.request({
					url: '/ridersApi/courier/courier/getCourierInfo',
					method: 'get',
					data: {

					}
				}).then((res) => {
					self.form = res.result

				})


			}
		},
		methods: {
			//提交数据
			submit() {
				if (!this.form.schoolId) {
					uni.showToast({
						title: '请选择学校',
						icon: 'none'
					})
					return
				}
				if (!this.form.courierName) {
					uni.showToast({
						title: '请填写名称',
						icon: 'none'
					})
					return
				}
				if (!this.form.courierPhone) {
					uni.showToast({
						title: '请填写联系方式',
						icon: 'none'
					})
					return
				}
				if (!this.form.idImgFront) {
					uni.showToast({
						title: '请上传身份证正面照片',
						icon: 'none'
					})
					return
				}
				if (!this.form.idImgBack) {
					uni.showToast({
						title: '请上传身份证背面照片',
						icon: 'none'
					})
					return
				}
				if (!this.form.perImgSelf) {
					uni.showToast({
						title: '请上传个人近照',
						icon: 'none'
					})
					return
				}

				this.iGlobal.request({
					// url:'/ridersApi/courier/courier/applyAccountInfo',
					url: '',
					method: 'POST',
					data: {
						...this.form
					}
				}).then((res) => {
					//重定向到审核页面
					uni.redirectTo({
						url: "/pages/apply/applyIn"
					})

				})

			},
			chooseSchool() {
				//获取用户的定位权限，跳转至地图页面
				if (!this.lat && !this.lon) {
					this.getUserLocation();
				}
				if (this.lat && this.lon) {
					uni.navigateTo({
						url: '/pages/school/school'
					});
				}
			},

			getUserLocation() {
				let vm = this
				wx.getSetting({
					success: (res) => {
						// res.authSetting['scope.userLocation'] == undefined    表示 初始化进入该页面
						// res.authSetting['scope.userLocation'] == false    表示 非初始化进入该页面,且未授权
						// res.authSetting['scope.userLocation'] == true    表示 地理位置授权
						// 拒绝授权后再次进入重新授权
						if (res.authSetting['scope.userLocation'] != undefined && res.authSetting[
								'scope.userLocation'] != true) {
							// console.log('authSetting:status:拒绝授权后再次进入重新授权', res.authSetting['scope.userLocation'])
							wx.showModal({
								title: '',
								content: '飞毛腿需要获取你的地理位置，请确认授权',
								success: function(res) {
									if (res.cancel) {
										wx.showToast({
											title: '拒绝授权',
											icon: 'none'
										})
									} else if (res.confirm) {
										wx.openSetting({
											success: function(dataAu) {
												// console.log('dataAu:success', dataAu)
												if (dataAu.authSetting[
														"scope.userLocation"] ==
													true) {
													//再次授权，调用wx.getLocation的API
													vm.getLocation(dataAu)
												} else {
													wx.showToast({
														title: '授权失败',
														icon: 'none'
													})
												}
											}
										})
									}
								}
							})
						}
						// 初始化进入，未授权
						else if (res.authSetting['scope.userLocation'] == undefined) {
							console.log('authSetting:status:初始化进入，未授权', res.authSetting['scope.userLocation'])
							//调用wx.getLocation的API
							vm.getLocation(res)
						}
						// 已授权
						else if (res.authSetting['scope.userLocation']) {
							console.log('authSetting:status:已授权', res.authSetting['scope.userLocation'])
							//调用wx.getLocation的API
							vm.getLocation(res)
						}
					}
				})
			},
			// 微信获得经纬度
			getLocation(userLocation) {
				let vm = this
				wx.getLocation({
					type: "gcj02",
					success: function(res) {
						var latitude = res.latitude
						var longitude = res.longitude
						vm.lat = latitude
						vm.lon = longitude
						if (vm.lat && vm.lon) {
							uni.navigateTo({
								url: '/pages/school/school'
							});
						}
					},
					fail: function(res) {
						// console.log('getLocation:fail', res)
						if (res.errMsg === 'getLocation:fail:auth denied') {
							wx.showToast({
								title: '拒绝授权',
								icon: 'none'
							})
							return
						}
						if (!userLocation || !userLocation.authSetting['scope.userLocation']) {
							vm.getUserLocation()
						} else if (userLocation.authSetting['scope.userLocation']) {
							wx.showModal({
								title: '',
								content: '请在系统设置中打开定位服务',
								showCancel: false,
								success: result => {}
							})
						} else {
							wx.showToast({
								title: '授权失败',
								icon: 'none'
							})
						}
					}
				})
			},

			uploadZM() {
				var self = this
				this.iGlobal.upLoad().then((res) => {
					self.form.idImgFront = res
				});
			},
			uploadFM() {
				var self = this
				this.iGlobal.upLoad().then((res) => {
					self.form.idImgBack = res
				});
			},
			uploadJZ() {
				var self = this
				this.iGlobal.upLoad().then((res) => {
					self.form.perImgSelf = res
				});
			},
			radioChange() {

			},
			NumSteps() {
				this.num = this.num == this.numList.length - 1 ? 0 : this.num + 1
			},

		}
	}
</script>

<style lang="scss">
	.input-class {
		text-align: right;
	}

	.fill {
		.pic {
			display: flex;
			flex-wrap: wrap;
			justify-content: space-between;

			.pic_01 {
				width: 46%;
				text-align: center;
				padding-bottom: 5px;
				line-height: 25px;

				img {
					width: 100%;
					height: 270rpx;
					border-radius: 5px;
				}
			}
		}
	}

	.radio-group {
		display: flex;
		justify-content: flex-start;

		.radio {
			padding: 10rpx 30rpx;
			border-radius: 6rpx;
			border: 2rpx solid $text-color-assist;
			color: $text-color-assist;
			font-size: $font-size-base;

			&.checked {
				background-color: $color-primary;
				color: $text-color-white;
				border: 2rpx solid $color-primary;
			}
		}
	}
</style>
