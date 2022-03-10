<template>
	<view>
		<button @click="login()">登录</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				}
		},
		onLoad() {

		},
		methods: {
			login() {
				uni.redirectTo({
					url:"../login/login"
				})
			}
		}
	}
</script>

<style>

</style>
