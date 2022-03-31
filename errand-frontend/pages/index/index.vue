<template>
	<view>
		<view class="navbar">
			<hx-navbar :back="false" :fixed="true" :left-slot="false" :right-slot="false"
				:backgroundColor="[1, ['#FED635', '#FED635']]">
				<view class="main-left" @click="chooseCity()">
					<view class="city">
						<view>{{location}}</view>
						<uni-icons type="arrowdown" color="#333333" size="22" />
					</view>
				</view>
				<view style="text-align: center;width: 65%;">
					<text>飞毛腿</text>
				</view>
			</hx-navbar>
		</view>
		<image src="../../static/index_icon/swich.png" mode="widthFix" class="response"></image>
		<view class="nav-list">
			<!-- 发布模块 -->
			<navigator hover-class="none" :url="'/pages/publish/' + item.extName" class="nav-li"
				navigateTo :class="'bg-'+item.color" v-for="(item,index) in elements" :key="index">
				<view class="nav-title">{{item.title}}</view>
				<view class="nav-name">{{item.name}}</view>
				<!-- <text :class="'cuIcon-' + item.cuIcon"></text> -->
			</navigator>
			
			<navigator url="../login/login">登录</navigator>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				//位置信息
				location: '',
				elements: [{
						title: '代取快递',
						// name: 'shop-jd.vue',
						color: 'cyan',
						// cuIcon: 'newsfill',
						extName: 'delivery'
					}, {
						title: '代买东西',
						// name: 'order.vue',
						color: 'red',
						// cuIcon: 'myfill',
						// extName: 'unicorn-page-order'
					}, {
						title: '代打饭',
						// name: 'video-slide.vue',
						color: 'green',
						// cuIcon: 'playfill',
						// extName: 'unicorn-page-video-slide'
					},
					{
						title: '代拿外卖',
						// name: 'video-slide.vue',
						color: 'blue',
						// cuIcon: 'playfill',
						// extName: 'unicorn-page-video-slide'
					}
				],
			}
		},
		onPullDownRefresh() {
			console.log('refresh');
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		onLoad() {
			var that = this;
			//监听城市选择页
			uni.$on("selectCity", (selectCity) => {
				that.location = selectCity
			})
			//获取位置信息
			this.getLocation();
		},
		methods: {
			//获取位置信息
			getLocation() {
				uni.getLocation({
					type: 'gcj02',
					success: (res) => {
						let that = this
						const latitude = res.latitude;
						const longitude = res.longitude;
						uni.request({
							header: {
								"Content-Type": "application/text"
							},
							url: 'https://restapi.amap.com/v3/geocode/regeo?output=JSON&location=' +
								res.longitude + ',' + res.latitude +
								'&key=82421d28c27c16f380dacf07af12481f&radius=1000&extensions=all',
							success(re) {
								if (re.statusCode === 200) {
									that.location = re.data.regeocode.addressComponent.city
								} else {
									that.location = '未开启定位'
								}
							}
						});
					},
					fail: (res) => {
						that.location = '未开启定位'
					}
				});
			},

			/* 选择城市 */
			chooseCity() {
				uni.navigateTo({
					url: '../city/city'
				})
			},
		}
	}
</script>

<style>
	.city {
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: center;
		width: 100%;
		white-space: nowrap;
		font-family: "Times New Roman", Georgia, Serif;

	}

	/* #ifdef MP */
	.mp-placeholder {
		margin-right: 220upx;
	}

	/* #endif */


	.nav-list {
		margin-top: 60rpx;
		display: flex;
		flex-wrap: wrap;
		padding: 0px 40upx 0px;
		justify-content: space-between;
	}

	.nav-li {
		padding: 30upx;
		border-radius: 12upx;
		width: 45%;
		margin: 0 2.5% 40upx;
		background-size: cover;
		background-position: center;
		position: relative;
		z-index: 1;
	}

	.nav-li::after {
		content: "";
		position: absolute;
		z-index: -1;
		background-color: inherit;
		width: 100%;
		height: 100%;
		left: 0;
		bottom: -10%;
		border-radius: 10upx;
		opacity: 0.2;
		transform: scale(0.9, 0.9);
	}

	.nav-li.cur {
		color: #fff;
		background: rgb(94, 185, 94);
		box-shadow: 4upx 4upx 6upx rgba(94, 185, 94, 0.4);
	}

	.nav-title {
		font-size: 32upx;
		font-weight: 300;
	}

	.nav-title::first-letter {
		font-size: 40upx;
		margin-right: 4upx;
	}

	.nav-name {
		font-size: 28upx;
		text-transform: Capitalize;
		margin-top: 20upx;
		position: relative;
	}

	.nav-name::before {
		content: "";
		position: absolute;
		display: block;
		width: 40upx;
		height: 6upx;
		background: #fff;
		bottom: 0;
		right: 0;
		opacity: 0.5;
	}

	.nav-name::after {
		content: "";
		position: absolute;
		display: block;
		width: 100upx;
		height: 1px;
		background: #fff;
		bottom: 0;
		right: 40upx;
		opacity: 0.3;
	}

	.nav-name::first-letter {
		font-weight: bold;
		font-size: 36upx;
		margin-right: 1px;
	}

	.nav-li text {
		position: absolute;
		right: 30upx;
		top: 30upx;
		font-size: 52upx;
		width: 60upx;
		height: 60upx;
		text-align: center;
		line-height: 60upx;
	}

	.text-light {
		font-weight: 300;
	}

	@keyframes show {
		0% {
			transform: translateY(-50px);
		}

		60% {
			transform: translateY(40upx);
		}

		100% {
			transform: translateY(0px);
		}
	}

	@-webkit-keyframes show {
		0% {
			transform: translateY(-50px);
		}

		60% {
			transform: translateY(40upx);
		}

		100% {
			transform: translateY(0px);
		}
	}
</style>
