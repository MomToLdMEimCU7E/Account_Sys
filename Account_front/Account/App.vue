<script>
	export default {
		// onLaunch: function() {
		// 	console.warn('当前组件仅支持 uni_modules 目录结构 ，请升级 HBuilderX 到 3.1.0 版本以上！')
		// 	console.log('App Launch')
		// },
		// onShow: function() {
		// 	console.log('App Show')
		// },
		// onHide: function() {
		// 	console.log('App Hide')
		// }
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	@import '@/uni_modules/uni-scss/index.scss';
	@import "./tm-vuetify/mian.min.css";
	/* #ifndef APP-NVUE */
	@import '@/static/customicons.css';
	/* 主题包 */
	@import "./tm-vuetify/scss/theme.css";
	// 设置整个项目的背景色
	/* 引入iconfont图标 */
	@import url("static/iconfont-weapp-icon.css");
	@import "@/static/iconfont.css";
	// tabbaricon引用
	@import 'static/tabbar_icon.css';
	/*样式设置*/
	.t-icon{
		width:30px;
		height:30px;
	}
	page {
		background-color: #EEF8F8;
	}

	/* #endif */
	.example-info {
		font-size: 14px;
		color: #333;
		padding: 10px;
	}
</style>
