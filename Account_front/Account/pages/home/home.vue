<template>
	<view>
		<scroll-view scroll-y="true">
			<view class="pet" @click="gonavigate()">
				<image src="../../static/pet/pet1.gif" style="width: 100%;height: 650upx;"></image>
			</view>
			<view class="rcard">
				<uni-card :extra="'Lv.'+ level">
					<uni-row>
						<uni-col :span="12">
							<view class="left">
								<view class="title">
									今日事项
								</view>
								<tm-gap :gutter="10"></tm-gap>
								<view class="text">
									事件：{{thing}}
								</view>
								<tm-gap :gutter="10"></tm-gap>
								<view class="text">
									地点：{{pos}}
								</view>
								<tm-gap :gutter="10"></tm-gap>
								<view class="text">
									时间：{{time}}
								</view>
							</view>
						</uni-col>
						<uni-col :span="1" style="margin: 5% 0%;">
							<view class="divider"></view>
							<!-- <tm-divider :vertical="true" :width="1" model="dotted"></tm-divider> -->
						</uni-col>
						<uni-col :span="6" class="right">
							<view class="r_title">
								已记账
							</view>
							<view class="r_textarea">
								<view class="r_text">
									{{day}}
								</view>
								<view class="r_text2">
									天
								</view>
							</view>
						</uni-col>
					</uni-row>
				</uni-card>
			</view>
		</scroll-view>

		<bxTabber></bxTabber>
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
				day: 10,
				level: 1,
				thing: '无',
				time: '无',
				pos: '无'
			};
		},
		methods: {
			gonavigate() {
				uni.navigateTo({
					url: '/subpkg/pet/pet'
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #fbfbfb;
	}

	.pet {
		display: flex;
		justify-content: center;
		// margin-top: 100%;
		width: 100%;
		// width: 50%;
		// border: 1upx solid black;
		// border-radius: 50%;
	}

	.divider {
		width: 1px;
		height: 100px;
		margin: 5% 0%;
		background-color: #000000;
	}

	.rcard {
		display: block;
		// align-items: center;
		// margin-top: 20%;
		margin: 0% 5%;
	}

	.left .title {
		font-size: 120%;
		font-weight: bold;
	}

	.left .text {
		font-size: 105%;
		margin: 2% 0%;
	}

	.r_title {
		font-size: 120%;
		// font-weight: bold;
	}

	.r_text {
		// margin-top: 50%;
		font-size: 400%;
		font-weight: bold;
	}

	.r_text2 {
		margin-top: 15%;
		font-size: 120%;
		// font-weight: bold;
	}
	.r_textarea {
		margin-top: 60%;
		display: flex;
		justify-items: end;
	}
</style>