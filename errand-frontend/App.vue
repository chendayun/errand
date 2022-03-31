<script>
	export default {
		globalData: {
			mainHost: 'http://192.168.123.169:8081'
		},
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
	@import './common/common/fui-app.css';
	@import './components/firstui/fui-theme/fui-theme.css';
	@import "colorui/main.css";
	@import "colorui/icon.css";
	@import "common/common/main.css";
	@import "common/common/icon.css";
</style>
