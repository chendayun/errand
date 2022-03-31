<template>
	<view class="delivery-root">
		<view class="delivery-item delivery-item-border">
			<view class="delivery-item-title">收件人：</view>
		</view>
		<view class="delivery-item delivery-item-border">
			<input class="delivery-item-title" placeholder="输入姓名" />
		</view>
		<view class="delivery-item delivery-item-border">
			<view class="delivery-item-title">联系电话：</view>
		</view>
		<view class="delivery-item delivery-item-margin">
			<input class="delivery-item-title" placeholder="输入手机号" />
		</view>

		<view class="delivery-item delivery-item-border">
			<view class="delivery-item-title">收件地址：</view>
		</view>
		<view class="delivery-item delivery-item-border">
			<input class="delivery-item-title" placeholder="如:宿舍楼2栋804" />
		</view>
		<view class="delivery-item delivery-item-border">
			<view class="delivery-item-title">服务时间：</view>
		</view>
		<view class="delivery-item delivery-item-margin">
			<input class="delivery-item-title" placeholder="如:中午12点" />
		</view>
		<view class="delivery-item delivery-item-border">
			<view class="delivery-item-title">快递单号</view>
		</view>
		<view class="delivery-item delivery-item-margin">
			<input class="delivery-item-title" placeholder="如:中通-1-13XX" />
		</view>
		<view class="delivery-item-title delivery-item-txt delivery-item-margin">
			<text>大概重量：</text>
		</view >
		<view  class="delivery-item delivery-item-margin">
			<fui-radio-group  name="radio" v-model="val" @change="change">
				<fui-label v-for="(item,index) in radioItems" :key="index">
					<fui-list-cell>
						<view >
							<fui-radio :checked="item.checked" :value="item.value" color="#FFB703"
								borderColor="#B2B2B2">
							</fui-radio>
							<text class="delivery-item-fui-text">{{item.name}}</text>
						</view>
					</fui-list-cell>
				</fui-label>
			</fui-radio-group>
		</view>
		<view class="delivery-item delivery-item-border">
			<view class="delivery-item-title">快递单号</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				val: '1',
				radioItems: [{
						name: '1kg以下',
						value: '1',
						checked: true
					},
					{
						name: '1-3kg',
						value: '2',
						checked: false
					},
					{
						name: '3kg以上',
						value: '3',
						checked: false
					}
				]
			}
		},
		methods: {
			change(e) {
				console.log('change:' + e.detail.value)
			}
		}
	}
</script>

<style>
	
	.delivery-root {
		background-color: #EDEDED;
		height: 100%;

	}

	.delivery-item {

		position: relative;
		padding: 30rpx 30rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		background-color: white;
		color: #030303;

	}

	.delivery-item-title {
		font-size: 32rpx;
	}

	.delivery-item-txt {
		font-weight: bold;
		padding-left: 16rpx;
	}
	.delivery-item-fui-text{
		padding-left: 16rpx;
	}

	.delivery-item-border {
		border-bottom: 1rpx solid #f3f3f3;
	}

	.delivery-item-margin {
		margin-bottom: 16rpx;
	}
</style>
