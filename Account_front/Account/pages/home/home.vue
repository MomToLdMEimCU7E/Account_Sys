<template>
	<view>
		<view class="pet" @click="gonavigate()">
			<img src="../../static/pet/pet1.gif"><img>
		</view>
		<!-- <view class="fcard">
			<tm-card subTitle="lv.1">
				<template v-slot:content="{ text }">
					<tm-col :grid="6" justify="left">
						<view class="lcard">
							<h3>今日事项</h3>
							<tm-gap :gutter="10"></tm-gap>
							事件
							<tm-gap :gutter="10"></tm-gap>
							地点
							<tm-gap :gutter="10"></tm-gap>
							时间
						</view>

					</tm-col>
					<tm-col :grid="1" justify="right">
						<tm-divider :vertical="true" :width="1" model="dotted"></tm-divider>
					</tm-col>
					<tm-col :grid="5" justify="left">
						已记账
						<view class="rcard">
							{{ day }}天
						</view>
					</tm-col>
				</template>
			</tm-card>
		</view> -->
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
	.pet {
		display: block;
		// justify-content: center;
		// align-items: center;
		margin: 10% auto;
		width: 100%;
		height: 450upx;
		// border: 1upx solid black;
		// border-radius: 1000upx;
		overflow: hidden;
	}
	.rcard{
		display: flex;
		margin-top: 20%;
		font-size: 200%;
		font-weight: bold;
	}
</style>
