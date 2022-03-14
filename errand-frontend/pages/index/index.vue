<template>
	<view>
		<view>
			<hx-navbar :back="false" :fixed="true" :left-slot="false" :right-slot="false" :backgroundColor= "[1, ['#FED635', '#FED635']]">
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
	</view>
</template>

<script>
	export default {
		data() {
			return {
				//位置信息
				location: '',
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
</style>
