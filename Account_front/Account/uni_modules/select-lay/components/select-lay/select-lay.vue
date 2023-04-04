<template>
	<view class="uni-select-lay" :style="{'z-index':zindex}">
		<input type="text" :name="name" v-model="value" class="uni-select-input">
		<view class="uni-select-lay-select" :class="{'active':active}">
			<!-- 禁用mask -->
			<view class="uni-disabled" v-if="disabled"></view>
			<!-- 禁用mask -->
			<!-- 清空 -->
			<view class="uni-select-lay-input-close" v-if="changevalue!=''&&this.active">
				<text @click.stop="removevalue"></text>
			</view>
			<!-- 清空 -->
			<input type="text" class="uni-select-lay-input" :class="{active:changevalue!=''&&changevalue!=placeholder}"
				v-model="changevalue" :disabled="disabled" :placeholder="placeholder" @focus="unifocus"
				@input="intchange" @blur="uniblur">
			<view class="uni-select-lay-icon" :class="{disabled:disabled}" @click.stop="select"><text></text></view>
		</view>
		<scroll-view class="uni-select-lay-options" :scroll-y="true" v-show="active" @scroll="selectmove"
			@touchstart="movetouch">
			<template v-if="!changes">
				<view class="uni-select-lay-item" v-if="showplaceholder" :class="{active:value==''}"
					@click.stop="selectitem(-1,null)">
					{{placeholder}}
				</view>
				<view class="uni-select-lay-item" :class="{active:value==item[svalue]}" v-for="(item,index) in options"
					:key="index" @click.stop="selectitem(index,item)">{{item[slabel]}}</view>
			</template>
			<!-- 搜索 -->
			<template v-else>
				<template v-if="vlist.length>0">
					<view class="uni-select-lay-item" :class="{active:value==item[svalue]}"
						v-for="(item,index) in vlist" :key="index" @click.stop="selectitem(index,item)">{{item[slabel]}}
					</view>
				</template>
				<template v-else>
					<view class="nosearch">{{changesValue}}</view>
				</template>
			</template>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		name: "select-lay",
		props: {
			disabled: {
				type: Boolean,
				default: false
			},
			zindex: {
				type: Number,
				default: 999
			},
			options: {
				type: Array,
				default () {
					return []
				}
			},
			name: {
				type: String,
				default: ''
			},
			value: {
				type: String,
				default: ''
			},
			placeholder: {
				type: String,
				default: '请选择'
			},
			showplaceholder: {
				type: Boolean,
				default: true
			},
			slabel: {
				type: String,
				default: 'label'
			},
			svalue: {
				type: String,
				default: 'value'
			}
		},
		data() {
			return {
				active: false, //组件是否激活，
				isfocus: false, //是否有焦点
				isremove: false, //是否是因为点击清空才导致的失去焦点
				ismove: false, //是否是因为移动才失去焦点
				changevalue: "", //搜索框同步
				oldvalue: "", //数据回滚
				changes: false, //正在搜索
				changesValue: "",
				vlist: [], //搜索框查询的列表
				settimer: null //value改变定时器
			};
		},
		mounted() {
			this.itemcheck();
		},
		watch: {
			//value改变
			value() {
				this.itemcheck();
			},
			//初始化数组
			options() {
				// 此处判断是否有初始value,存在则判断显示文字
				this.itemcheck();
			}
		},
		methods: {
			//判断数组跟当前active值
			itemcheck() {
				// 此处判断是否有初始value,存在则判断显示文字
				if (this.value != "") {
					// 展示plachhoder
					//判断数组
					if (this.options.length > 0) {
						this.options.forEach(item => {
							if (this.value == item[this.svalue]) {
								this.oldvalue = this.changevalue = item[this.slabel];
								return;
							}
						})
					}
				} else {
					this.oldvalue = this.changevalue = "";
				}
			},
			//点击组件
			select() {
				if (this.disabled) return;
				this.active = !this.active;
				if (this.active) {
					this.changes = false;
				} else {
					this.changevalue = this.oldvalue;
				}
			},
			// 获得焦点
			unifocus() {
				if (this.disabled) return;
				this.active = true;
				this.changes = false;
				this.isfocus = true;
			},
			// 失去焦点
			uniblur() {
				this.isfocus = false;
				// bug   点击组件列会先触发失去焦点，此时组件列事件不执行
				setTimeout(() => {
					if (this.isremove || this.ismove) {
						this.isremove = false;
						this.ismove = false;
					} else {
						this.changevalue = this.oldvalue;
						this.isremove = false;
						this.active = false;
					}
				}, 153)
			},
			movetouch() {
				setTimeout(()=>{
					if(this.isfocus){
						 this.ismove=false;
						 return;
					}
					if (!this.ismove) this.ismove = true;
				},100)
				// this.changes = false;
			},
			selectmove() {
				setTimeout(()=>{
					if(this.isfocus){
						 this.ismove=false;
						 return;
					} 
					if (!this.ismove) this.ismove = true;
				},100)
				
				// this.changes = false;
			},
			//移除数据
			removevalue() {
				this.isremove = true;
				this.changes = false;
				this.changevalue = "";
			},
			//value 改变
			intchange() {
				if (this.changevalue == '') {
					this.changes = false;
					return;
				};
				if (this.oldvalue == this.changevalue) {
					return;
				}
				this.vlist = [];
				this.changes = true;
				this.changesValue = "正在搜索...";
				if (this.settimer) {
					clearTimeout(this.settimer)
				}
				this.settimer = setTimeout(() => {
					this.vlist = this.options.filter(item => {
						return item[this.slabel].includes(this.changevalue)
					});
					if (this.vlist.length === 0) {
						this.changesValue = "暂无匹配内容！";
					}
				}, 600)

			},

			//点击组件列
			selectitem(index, item) {
				this.changevalue = this.oldvalue;
				this.active = false;
				this.$emit("selectitem", index, item)
			}
		}
	}
</script>

<style lang="scss" scoped>
	.uni-select-lay {
		position: relative;
		z-index: 999;

		.uni-select-input {
			opacity: 0;
			position: absolute;
			z-index: -111;
		}

		// select部分 
		.uni-select-lay-select {
			user-select: none;
			position: relative;
			z-index: 3;
			height: 36px;
			padding: 0 30px 0 10px;
			box-sizing: border-box;
			border-radius: 4px;
			border: 1px solid rgb(229, 229, 229);
			display: flex;
			align-items: center;
			font-size: 14px;
			color: #999;

			.uni-disabled {
				position: absolute;
				left: 0;
				width: 100%;
				height: 100%;
				z-index: 19;
				cursor: no-drop;
				background: rgba(255, 255, 255, .5);
			}

			// input 框的清除按钮
			.uni-select-lay-input-close {
				position: absolute;
				right: 35px;
				top: 0;
				height: 100%;
				width: 15px;
				display: flex;
				align-items: center;
				justify-content: center;
				z-index: 3;
				cursor: pointer;

				text {
					position: relative;
					background: #fff;
					width: 13px;
					height: 13px;
					border-radius: 50%;
					border: 1px solid #bbb;

					&::before,
					&::after {
						content: "";
						position: absolute;
						left: 20%;
						top: 50%;
						height: 1px;
						width: 60%;
						transform: rotate(45deg);
						background-color: #bbb;
					}

					&::after {
						transform: rotate(-45deg);
					}
				}
			}

			.uni-select-lay-input {
				font-size: 14px;
				color: #999;
				display: block;
				width: 98%;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
				line-height: 30px;
				box-sizing: border-box;

				&.active {
					color: #333
				}
			}

			.uni-select-lay-icon {
				cursor: pointer;
				position: absolute;
				right: 0;
				top: 0;
				height: 100%;
				width: 30px;
				display: flex;
				align-items: center;
				justify-content: center;

				&::before {
					content: "";
					width: 1px;
					height: 100%;
					position: absolute;
					left: 0;
					top: 0;
					background-color: #e5e5e5;
				}

				text {
					display: block;
					width: 0;
					height: 0;
					border-width: 12rpx 12rpx 0;
					border-style: solid;
					border-color: #bbb transparent transparent;
					transition: .3s;
				}

				&.disabled {
					cursor: no-drop;

					text {
						width: 20rpx;
						height: 20rpx;
						border: 2px solid #ff0000;
						border-radius: 50%;
						transition: .3s;
						position: relative;
						z-index: 999;

						&::after {
							content: "";
							position: absolute;
							top: 50%;
							left: 0;
							width: 100%;
							height: 2px;
							margin-top: -1px;
							background-color: #ff0000;
							transform: rotate(45deg);

						}
					}
				}
			}

			&.active .uni-select-lay-icon {
				text {
					transform: rotate(180deg);
				}
			}
		}

		// options部分
		.uni-select-lay-options {
			user-select: none;
			position: absolute;
			top: calc(100% + 5px);
			left: 0;
			width: 100%;
			height: 500rpx;
			// overflow-y: auto;
			border-radius: 4px;
			border: 1px solid rgb(229, 229, 229);
			background: #fff;
			padding: 5px 0;
			box-sizing: border-box;
			z-index: 9;

			.uni-select-lay-item {
				padding: 0 10px;
				box-sizing: border-box;
				cursor: pointer;
				line-height: 2.5;
				transition: .3s;
				font-size: 14px;

				&.active {
					background: #007AFF;
					color: #fff;

					&:hover {
						background: #007AFF;
						color: #fff;
					}
				}

				&:hover {
					background-color: #f5f5f5;
				}
			}

			.nosearch {
				font-size: 16px;
				line-height: 3;
				text-align: center;
				color: #666;
			}
		}
	}
</style>
