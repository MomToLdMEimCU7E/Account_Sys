<template>
	<view>
		<scroll-view scroll-y="true" :enable-flex="true" style="overflow: auto;">
			<view class="bookShow" v-for="(item,index) in bookList" :key="index">
				<image :src="'../../static/' + item.bookkeepingCover +'.png'" mode="scaleToFill"
					style="width: 200upx;height: 300upx;"></image>
				<view class="titleShow">
					<uni-icons type="compose" size="30" color="#15D8AC"></uni-icons>
					<view class="title">{{item.bookkeepingName}}</view>
				</view>
				<view class="button" @click="GoToprodetail(index)">
					编辑账本
				</view>
			</view>
			<view class="navi" @click="openAddbookDialog()">
				<uni-icons type="plusempty"></uni-icons>添加账本
			</view>
		</scroll-view>
		<uni-popup ref="AddbookDialog" type="dialog" style="width: 120%;">
			<uni-popup-dialog type="info" cancelText="取消" confirmText="确定" title="添加账本" @confirm="addBookConfirm()">
				<view class="formView">
					<uni-forms :modelValue="bookData">
						<uni-forms-item label="名称" required>
							<uni-easyinput v-model="bookData.bookkeepingName" placeholder="请输入账本名称"></uni-easyinput>
						</uni-forms-item>
						<uni-forms-item label="类型" required>
							<uni-data-select v-model="bookData.bookkeepingTypeId" :localdata="bookkeepingType" @change="changebookkeepingTypeId"
								placeholder="请选择账本类型"></uni-data-select>
						</uni-forms-item>
					</uni-forms>
				</view>
			</uni-popup-dialog>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				uid: 1,
				bookList: [],
				bookData: {
					uid: '',
					bookkeepingTypeId: '',
					bookkeepingCover: '',
					bookkeepingName: '',
				},
				bookkeepingType: [{
						value: 1,
						text: "日常开销"
					},
					{
						value: 2,
						text: "人情往来"
					},
					{
						value: 3,
						text: "家庭账本"
					},
				],
			}
		},
		onShow() {
			this.getBookList();
		},
		methods: {
			GoToprodetail(index) {
				let item = encodeURIComponent(JSON.stringify(this.bookList[index]))
				uni.navigateTo({
					url: '/subpkg/bookdetail/bookdetail?item=' + item
				})
			},
			getBookList() {
				uni.$http.get('/book/getBookList?uid=' + this.uid).then(res => {
					if (res.data.msg !== "成功")
						return uni.$showMsg()
					else this.bookList = res.data.data.data;
					// console.log(this.bookList[0].bookkeepingCover)
				})
			},
			openAddbookDialog() {
				this.$refs.AddbookDialog.open();
			},
			changebookkeepingTypeId(e){
				this.bookData.bookkeepingTypeId =  e;
			},
			addBookConfirm(){
				this.bookData.uid = 1;
				this.bookData.bookkeepingCover = 'daily';
				uni.$http.post('/book/createBook', this.bookData).then(res => {
					if (res.data.msg === "成功"){
						this.getBookList();
						return uni.showToast({
							title: '添加成功！',
							duration: 1500,
							icon: 'none',
						});
						
					}
						
					else return uni.showToast({
						title: '添加失败！',
						duration: 1500,
						icon: 'none',
					})
				})
				
				this.$refs.AddbookDialog.close()
			}
		}
	}
</script>

<style>
	.bookShow {
		display: flex;
		background-color: #fff;
		box-shadow: 0 3px 8px #cac6;
		padding: 10px;
		margin: 5%;

		justify-content: space-around;
	}

	.titleShow {
		display: flex;
		justify-content: center;
		/* align-items: center; */
	}

	.titleShow .title {
		font-size: 130%;
		font-weight: bold;
		margin-left: 5%;
		width: 120px;
	}

	.bookShow .button {
		background-color: #56DFC0;
		border-radius: 5%;
		box-shadow: 0 3px 8px #cac6;
		display: flex;
		justify-content: center;
		align-items: center;
		align-self: flex-end;
		height: 32px;
		margin: 0%;
		opacity: 0.9;
		width: 72px;
	}

	.navi {
		background-color: #56DFC0;
		box-shadow: 0 3px 8px rgba(0, 37, 204, 0.2);
		display: flex;
		justify-content: center;
		align-items: center;
		height: 100upx;
		margin: 0% 5%;
		opacity: 0.9;
	}

	.formView {
		/* padding: 20px; */
		display: flex;
		justify-content: flex-start;
		align-items: center;
	}
</style>