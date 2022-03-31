<template>
	<view>
		<view class="c1">
			<test>左边</test>
		</view>

		<view class="c2">
			<test>右边</test>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				val: '1',
				radioItems: [{
						name: '小于18岁',
						value: '1',
						checked: true
					},
					{
						name: '18~28岁',
						value: '2',
						checked: false
					},
					{
						name: '29~40岁',
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
	page {
		font-weight: normal;
	}

	.c1 {
		width: 50%;
		float: left;
		background-color: #ff5500;
	}

	.c2 {
		background-color: #55ff00;
		align-items: center;
		width: 50%;
		float: right;
	}
</style>
