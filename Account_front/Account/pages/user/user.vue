<template>
	<view>
		<scroll-view scroll-y="true">
			<view class="content-item">
				<view class="user-info">
					<view>
						<image src="/static/user.jpg" class="img" :mode="aspectFit"></image>
					</view>
					<view class="name">
						<text>{{userName}}</text>
					</view>
				</view>
			</view>
			<bxTabber></bxTabber>
			<view class="content-item">
				<view class="content-show">
					<view class="money-item">
						<text>
							{{accountDay}}
						</text>
						<text style="font-size: 12px;color: #919293;">
							记账天数
						</text>
					</view>
					<view class="money-item">
						<text>
							{{accountCount}}
						</text>
						<text style="font-size: 12px;color: #919293;">
							记账笔数
						</text>
					</view>
				</view>

			</view>
			<view class="content-item">
				<view class="content-show">
					<view class="order-tab">
						<uni-icons type="calendar" size="30" color="#15D8AC"></uni-icons>
						<text class="order-title">记账年报</text>
					</view>
					<navigator url="/subpkg/property/property">
						<view class="order-tab">
							<uni-icons type="wallet" size="30" color="#15D8AC"></uni-icons>
							<text class="order-title">资产管理</text>
						</view>
					</navigator>

					<view class="order-tab">
						<uni-icons type="icon-yiliao" custom-prefix="iconfont" size="30" color="#15D8AC"></uni-icons>
						<text class="order-title">健康管理</text>
					</view>
				</view>
			</view>
			<view class="content-item">
				<view class="title">
					<text>我的账本</text>
					<navigator url="/subpkg/bookman/bookman" class="b-setting">
						<uni-icons type="gear" size="22" color="#15D8AC"></uni-icons>
						<text>账本管理</text>
					</navigator>
				</view>
				<scroll-view scroll-x="true" class="b_scroll" enable-flex="true">
					<view class="content-show" style="justify-content: start;">
						<view class="b_list" v-for="(item,index) in bookList" :key="index">
							<image :src="item.bookKeepingCover" mode="scaleToFill"
								style="width: 150upx;height: 200upx;"></image>
							<text class="b_name">{{item.bookKeepingName}}</text>
						</view>
					</view>
				</scroll-view>
			</view>
			<uni-card :isShadow="true" title="设置" mode="basic" @click="clickCard">
				<view class="settingbox">
					<text class="switchTx">老年模式</text>
					<text class="switchBu">
						<tm-switch v-model="on"></tm-switch>
					</text>
				</view>
			</uni-card>
		</scroll-view>
		<bx-tabber></bx-tabber>
	</view>
</template>

<script>
	import bxTabber from '@/components/bx-tabber.vue';
	export default {
		components: {
			bxTabber
		},
		data() {
			return {
				accountCount: '111',
				accountDay: '111',
				userName: '测试用户',
				bookList: [{
						bookKeepingCover: '../../static/daily.png',
						bookKeepingName: '日常开销'
					},
					{
						bookKeepingCover: '../../static/daily.png',
						bookKeepingName: '日常开销'
					},
					{
						bookKeepingCover: '../../static/daily.png',
						bookKeepingName: '日常开销'
					},
					{
						bookKeepingCover: '../../static/daily.png',
						bookKeepingName: '日常开销'
					},
					{
						bookKeepingCover: '../../static/daily.png',
						bookKeepingName: '日常开销'
					},
				]
			}
		},
		methods: {

		}
	}
</script>

<style>
	.container {
		width: 100%;
	}

	.profile {
		width: 100%;
	}

	.user-info {
		/* position: absolute;
		top: 100px;
		left: 30px; */
		display: flex;
		align-items: center;
		background-color: #13D0A5;
		height: 250upx;
		justify-content: flex-start;
	}

	.user-info .img {
		margin: 5% 10%;
		width: 200upx;
		height: 200upx;
		border-radius: 50%;
	}

	.user-info .name {
		margin-left: 5%;
		font-size: 120%;
		/* display: flex; */
	}

	.content-item {
		display: flex;
		margin: 10px;
		flex-direction: column;
		border-radius: 4px;
		background-color: #FFFFFF;
		justify-content: space-around;
	}

	.content-item .title {
		display: flex;
		justify-content: space-between;
		padding: 15px;
		color: #5A5B5C;
		font-size: 16px;
		font-weight: bold;
	}

	.content-show {
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		align-items: center;
	}

	.money-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 20px;
	}

	.service-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 20px;
	}

	.service-item .service-text {
		font-size: 12px;
		color: #919293;
	}

	.order-tab {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 15px;
	}

	.order-title {
		font-size: 14px;
		padding-top: 10px;
		color: #919293;
		padding-bottom: 8px;
	}

	.settingbox {
		flex-flow: row;
		justify-content: flex-start;
		display: flex;
	}

	.switchTx {
		display: flex;
		justify-content: flex-start;
		width: 200%;
	}

	.switchBu {
		display: flex;
		justify-content: flex-end;
	}

	.b-setting {
		display: flex;
		justify-content: center;
	}

	.b_list {
		padding: 10px;
		display: flex;
		flex-flow: column;
		justify-content: center;
		align-items: center;
	}

	.b_scroll {
		white-space: nowrap;
		justify-content: ;
		/* width: 475px; */
	}
</style>