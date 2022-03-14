<template>
	<view>
		<hx-navbar  :back="false" :fixed="true" :left-slot="false" :right-slot="false">
			<view class="main-left" @click="info()">
				<view class="city">
					<view>新疆</view>
					<uni-icons type="arrowdown" color="#333333" size="22" />
				</view>
			</view>
			<view style="text-align: center;width: 60%;">
				<text>首页</text>
			</view>
			
		</hx-navbar>

	</view>
</template>

<script>
	export default {
		data() {
			return {}
		},
		onPullDownRefresh() {
			console.log('refresh');
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			info2() {
				uni.showToast({
					title: '扫码',
					icon: 'success'
				});
			},
			info() {
				uni.showToast({
					title: "选新疆",
					icon: 'success'
				});
			},
			confirm(e) {
				console.log(e.detail.value)
				uni.showToast({
					title: e.detail.value,
					icon: 'none'
				});
			}
		}
	}
</script>

<style>
	.ctn {
		border-radius: 40px;
		padding: 4px 15px;
		border: 1px solid #e3e3e3;
		width: 100%;
		display: flex;
		line-height: 44rpx;
		margin: 0 10px;
	}

	.city {
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: center;
		width: 100%;
		white-space: nowrap;
	}

	/* #ifdef MP */
	.mp-placeholder {
		margin-right: 220upx;
	}

	/* #endif */
</style>
