<template>
  <div class="app-container home">
    <!-- 顶部横幅 -->
    <div class="banner">
      <div class="banner-content">
        <h1>低代码快速开发平台</h1>
        <p class="tagline">基于SpringBoot的快速开发框架，助力企业高效构建应用系统</p>
        <div class="banner-actions">
          <el-button type="primary" size="medium" @click="goTarget('http://www.hysc.cn')">
            <i class="el-icon-reading"></i> 立即访问官网
          </el-button>
          <el-button type="success" size="medium" @click="goTarget('http://www.hysc.cn')">
            <i class="el-icon-download"></i> 获取代码
          </el-button>
        </div>
      </div>
    </div>

    <!-- 平台介绍 -->
    <div class="section">
      <h2 class="section-title"><i class="el-icon-s-home"></i> 平台技术框架介绍</h2>
      <el-row :gutter="20">
        <el-col :sm="24" :lg="12">
          <div class="introduction">
            <p>
              低代码快速开发平台是一款基于SpringBoot的快速开发框架，旨在简化企业级应用系统的开发过程。平台提供了一站式的开发解决方案，涵盖权限管理、代码生成、流程设计、报表设计、数据可视化等核心功能，让开发者能够专注于业务逻辑，而非重复性代码。
            </p>
            <p>
              <b>核心优势：</b>
            </p>
            <ul>
              <li><i class="el-icon-check"></i> <b>零代码/低代码</b>：通过可视化界面配置，快速生成应用模块</li>
              <li><i class="el-icon-check"></i> <b>多端适配</b>：完全响应式布局，支持PC、平板、手机等设备</li>
              <li><i class="el-icon-check"></i> <b>技术先进</b>：采用最流行的技术栈，确保系统高性能与可维护性</li>
              <li><i class="el-icon-check"></i> <b>生态完善</b>：提供丰富的扩展组件和插件，满足各类业务需求</li>
              <li><i class="el-icon-check"></i> <b>持续迭代</b>：行业驱动，持续迭代更新</li>
            </ul>
          </div>
        </el-col>
        
        <el-col :sm="24" :lg="12">
          <div class="tech-stack">
            <h3><i class="el-icon-setting"></i> 技术选型</h3>
            <el-row :gutter="20">
              <el-col :span="12">
                <div class="tech-category">
                  <h4><i class="el-icon-s-platform"></i> 后端技术</h4>
                  <ul>
                    <li>SpringBoot 2.7+</li>
                    <li>SpringCloud 2021.0.9</li>
                    <li>Nacos 2.0.4</li>
                    <li>Sentinel 1.8.1</li>
                    <li>Seata 1.5.2</li>
                    <li>Minio 8.4.1</li>
                    <li>MyBatis Plus 3.5.1</li>
                  </ul>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="tech-category">
                  <h4><i class="el-icon-mobile-phone"></i> 前端技术</h4>
                  <ul>
                    <li>Vue 3.2</li>
                    <li>Element Plus 2.2.10</li>
                    <li>Pinia 2.0</li>
                    <li>Axios 0.28.1</li>
                    <li>Echarts 5.4.0</li>
                    <li>Quill 2.0.2</li>
                    <li>Vue Router 4.1</li>
                  </ul>
                </div>
              </el-col>
            </el-row>
            <div class="tech-badges">
              <el-tag type="success" size="small" effect="dark">前后端分离</el-tag>
              <el-tag type="info" size="small" effect="dark">微服务架构</el-tag>
              <el-tag type="warning" size="small" effect="dark">多端适配</el-tag>
              <el-tag type="danger" size="small" effect="dark">持续迭代</el-tag>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 平台发展历史 -->
    <div class="section">
      <h2 class="section-title"><i class="el-icon-date"></i> 平台发展历史</h2>
      <div class="timeline">
        <div class="timeline-item" v-for="(version, index) in timelineVersions" :key="index">
          <div class="timeline-dot" :class="{'active': index === 0}"></div>
          <div class="timeline-content">
            <div class="timeline-header">
              <span class="version-tag">v{{ version.version }}</span>
              <span class="date">{{ version.date }}</span>
            </div>
            <div class="timeline-description">
              <p>{{ version.description }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 公告与通知 -->
    <div class="section">
      <h2 class="section-title"><i class="el-icon-bell"></i> 公告与通知</h2>
      <el-card class="announcement-card">
        <div class="announcement-list">
          <div class="announcement-item" v-for="(announcement, index) in announcements" :key="index">
            <div class="announcement-header">
              <span class="announcement-type" :class="announcement.type">{{ announcement.type }}</span>
              <span class="announcement-date">{{ announcement.date }}</span>
            </div>
            <div class="announcement-content">
              <p>{{ announcement.title }}</p>
              <p class="announcement-desc">{{ announcement.description }}</p>
            </div>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 联系信息 -->
    <div class="section">
      <h2 class="section-title"><i class="el-icon-s-custom"></i> 联系我们</h2>
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="8">
          <el-card class="contact-card">
            <template #header>
              <div class="clearfix">
                <span><i class="el-icon-location-outline"></i> 官网与文档</span>
              </div>
            </template>
            <div class="contact-info">
              <p>
                <i class="el-icon-s-promotion"></i> 官网：<el-link
                  href="http://www.hysc.cn"
                  target="_blank"
                  type="primary"
                  >http://www.hysc.cn</el-link
                >
              </p>
              <p>
                <i class="el-icon-document"></i> 开发文档：<el-link
                  href="http://www.hysc.cn/doc"
                  target="_blank"
                  type="primary"
                  >http://www.hysc.cn/doc</el-link
                >
              </p>
              <p>
                <i class="el-icon-s-finance"></i> 开源代码：<el-link
                  href="https://gitee.com/y_project/RuoYi"
                  target="_blank"
                  type="primary"
                  >https://gitee.com/y_project/RuoYi</el-link
                >
              </p>
            </div>
          </el-card>
        </el-col>
        
        <el-col :xs="24" :sm="24" :md="12" :lg="8">
          <el-card class="contact-card">
            <template #header>
              <div class="clearfix">
                <span><i class="el-icon-chat-line-round"></i> 技术支持</span>
              </div>
            </template>
            <div class="contact-info">
              <p>
                <i class="el-icon-s-custom"></i> QQ交流群：<el-link
                  href="http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=Z6j1amUmIPBXamuZzxpwjY8GwaP45XB6&authKey=zfSnxECfnScfY1HLCWilSqCq%2BmSVtr%2Bi%2F4oXeBcuQVwlq6XlrAfbZ0awp3%2B4Bdec&noverify=0&group_code=158753145"
                  target="_blank"
                  type="primary"
                  >190188591</el-link
                >
              </p>
              <p>
                <i class="el-icon-s-comment"></i> 技术交流：<el-link
                  href="http://www.hysc.cn/"
                  target="_blank"
                  type="primary"
                  >社区讨论区</el-link
                >
              </p>
              <p>
                <i class="el-icon-s-help"></i> 常见问题：<el-link
                  href="http://www.hysc.cn/faq.html"
                  target="_blank"
                  type="primary"
                  >常见问题解答</el-link
                >
              </p>
            </div>
          </el-card>
        </el-col>
        
        <el-col :xs="24" :sm="24" :md="12" :lg="8">
          <el-card class="contact-card">
            <template #header>
              <div class="clearfix">
                <span><i class="el-icon-s-data"></i> 版本信息</span>
              </div>
            </template>
            <div class="contact-info">
              <p>
                <i class="el-icon-s-flag"></i> <b>当前版本:</b> <span>v{{ currentVersion }}</span>
              </p>
              <p>
                <i class="el-icon-date"></i> <b>最新更新:</b> <span>{{ latestUpdate }}</span>
              </p>
              <p>
                <i class="el-icon-s-promotion"></i> <b>更新日志:</b> <el-link
                  href="https://doc.ruoyi.vip/ruoyi/update.html"
                  target="_blank"
                  type="primary"
                  >查看更新日志</el-link
                >
              </p>
              <p class="version-stats">
                <i class="el-icon-star-on"></i> <b>累计下载:</b> 36,000+ 次
                <br>
                <i class="el-icon-s-finance"></i> <b>GitHub Stars:</b> 12,000+
              </p>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 底部 -->
    <div class="footer">
      <p>Copyright © 2022-2027 低代码快速开发平台 | 京ICP备18046899号 | 京公网安备 44030902003184号</p>
      <p>Powered by <a href="http://www.hysc.cn" target="_blank">低代码开发框架</a> | <a href="https://gitee.com/y_project/RuoYi" target="_blank">开源项目</a></p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

// 当前版本信息
const currentVersion = ref('3.6.5');
const latestUpdate = ref('2025-11-13');

// 平台发展历史时间轴
const timelineVersions = ref([
  {
    version: '1.0.0',
    date: '2020-05-20',
    description: '微服务系统正式发布，提供基础权限管理和代码生成功能'
  },
  {
    version: '2.0.0',
    date: '2020-06-10',
    description: '引入Sentinel流量控制，优化菜单管理与系统架构'
  },
  {
    version: '2.2.0',
    date: '2021-10-10',
    description: '移除OAuth2改为Redis，新增在线用户会话管理，优化代码生成功能'
  },
  {
    version: '2.5.0',
    date: '2021-02-02',
    description: '增加分布式事务seata支持，优化代码生成模板与导出功能'
  },
  {
    version: '3.3.0',
    date: '2021-12-13',
    description: '新增配套Vue3前端版本，优化权限认证与用户管理'
  },
  {
    version: '3.4.0',
    date: '2022-01-24',
    description: '新增Vue3代码生成模板，优化图片预览与压缩插件'
  },
  {
    version: '3.5.0',
    date: '2022-04-11',
    description: '优化TopNav菜单，新增数据权限验证，提升系统性能'
  },
  {
    version: '3.6.0',
    date: '2022-07-16',
    description: '支持Excel导出字体颜色，优化用户头像上传与权限控制'
  },
  {
    version: '3.6.1',
    date: '2022-10-01',
    description: '支持多权限字符匹配，优化登录日志与密码安全'
  },
  {
    version: '3.6.2',
    date: '2023-01-16',
    description: '优化代码生成与数据字典功能，提升系统稳定性'
  },
  {
    version: '3.6.3',
    date: '2023-07-07',
    description: '新增登录IP黑名单限制，优化操作日志与数据安全'
  },
  {
    version: '3.6.4',
    date: '2024-09-01',
    description: '支持全局数据存储用户编号，优化Excel导入与导出'
  },
  {
    version: '3.6.5',
    date: '2025-11-13',
    description: '使用SpringDoc代替Swagger，全面升级依赖库，提升系统安全性'
  }
]);

// 公告与通知
const announcements = ref([
  {
    type: '重要通知',
    date: '2025-11-10',
    title: '3.6.5版本正式发布',
    description: '本次更新全面升级了系统依赖库，修复了多个安全漏洞，优化了代码生成和Excel导出功能。'
  },
  {
    type: '新功能',
    date: '2025-10-25',
    title: '新增低代码可视化配置功能',
    description: '通过拖拽式界面，可快速配置表单、列表和页面，大幅降低开发门槛。'
  },
  {
    type: '社区活动',
    date: '2025-10-15',
    title: '开源社区开发者大会即将举办',
    description: '11月5日将在北京举办线下开发者大会，邀请优秀开发者分享实战经验。'
  },
  {
    type: '更新提醒',
    date: '2025-09-30',
    title: '2025年12月起将停止对旧版本支持',
    description: '3.5.0及以下版本将不再提供安全更新和功能支持，请尽快升级。'
  }
]);

function goTarget(url) {
  window.open(url, '__blank');
}
</script>

<style scoped lang="scss">
.home {
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  color: #676a6c;
  line-height: 1.6;
  
  .banner {
    background: linear-gradient(135deg, #4b6df2, #6a89ff);
    color: white;
    padding: 60px 20px;
    text-align: center;
    margin-bottom: 40px;
    border-radius: 10px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    
    .banner-content {
      max-width: 1200px;
      margin: 0 auto;
      
      h1 {
        font-size: 36px;
        margin-bottom: 20px;
        font-weight: 300;
      }
      
      .tagline {
        font-size: 20px;
        margin-bottom: 30px;
        opacity: 0.9;
      }
      
      .banner-actions {
        display: flex;
        justify-content: center;
        gap: 20px;
        margin-top: 30px;
        
        .el-button {
          padding: 12px 30px;
          font-size: 16px;
          border-radius: 50px;
          transition: all 0.3s;
          
          &:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
          }
        }
      }
    }
  }
  
  .section {
    margin-bottom: 40px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 15px rgba(0, 0, 0, 0.05);
    padding: 30px;
    
    .section-title {
      margin-bottom: 25px;
      padding-bottom: 10px;
      border-bottom: 2px solid #f0f2f5;
      color: #4b6df2;
      font-weight: 500;
      
      i {
        margin-right: 10px;
      }
    }
    
    .introduction {
      p {
        margin-bottom: 15px;
        font-size: 15px;
        
        b {
          font-weight: 700;
          color: #4b6df2;
        }
      }
      
      ul {
        padding-left: 25px;
        margin-top: 20px;
        
        li {
          margin-bottom: 10px;
          list-style-type: none;
          position: relative;
          
          i {
            position: absolute;
            left: 0;
            color: #4b6df2;
          }
        }
      }
    }
    
    .tech-stack {
      h3 {
        margin-bottom: 20px;
        color: #4b6df2;
      }
      
      .tech-category {
        background: #f8fafd;
        border-radius: 8px;
        padding: 15px;
        margin-bottom: 15px;
        border-left: 4px solid #4b6df2;
        
        h4 {
          margin-bottom: 15px;
          color: #4b6df2;
          font-size: 16px;
        }
        
        ul {
          padding-left: 10px;
          
          li {
            margin-bottom: 8px;
            list-style-type: none;
            position: relative;
            
            &:before {
              content: "•";
              color: #4b6df2;
              font-weight: bold;
              margin-right: 5px;
            }
          }
        }
      }
      
      .tech-badges {
        display: flex;
        gap: 10px;
        margin-top: 15px;
        flex-wrap: wrap;
      }
    }
    
    .timeline {
      position: relative;
      
      .timeline-item {
        position: relative;
        padding: 25px 0;
        margin-left: 40px;
        
        &:not(:last-child) {
          margin-bottom: 40px;
        }
        
        .timeline-dot {
          position: absolute;
          left: -40px;
          top: 0;
          width: 20px;
          height: 20px;
          border-radius: 50%;
          background: #4b6df2;
          border: 3px solid white;
          box-shadow: 0 0 0 2px #4b6df2;
          
          &.active {
            background: #fff;
            border-color: #4b6df2;
          }
        }
        
        .timeline-content {
          background: #f8fafd;
          border-radius: 8px;
          padding: 20px;
          box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
          
          .timeline-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            
            .version-tag {
              background: #4b6df2;
              color: white;
              padding: 3px 10px;
              border-radius: 4px;
              font-weight: bold;
              font-size: 14px;
            }
            
            .date {
              color: #8c939d;
              font-size: 13px;
            }
          }
          
          .timeline-description {
            p {
              margin-bottom: 0;
              font-size: 14px;
              line-height: 1.5;
            }
          }
        }
      }
      
      &:before {
        content: "";
        position: absolute;
        top: 0;
        bottom: 0;
        width: 4px;
        background: #eaeaea;
        left: -25px;
        border-radius: 2px;
      }
    }
    
    .announcement-card {
      .announcement-list {
        .announcement-item {
          margin-bottom: 25px;
          padding-bottom: 20px;
          border-bottom: 1px solid #f0f2f5;
          
          &:last-child {
            margin-bottom: 0;
            padding-bottom: 0;
            border-bottom: none;
          }
          
          .announcement-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            
            .announcement-type {
              padding: 3px 10px;
              border-radius: 4px;
              font-size: 13px;
              font-weight: 500;
              text-transform: uppercase;
              
              &.重要通知 {
                background: #ff6b6b;
                color: white;
              }
              
              &.新功能 {
                background: #4cc967;
                color: white;
              }
              
              &.社区活动 {
                background: #6b72f9;
                color: white;
              }
              
              &.更新提醒 {
                background: #f9c86b;
                color: #666;
              }
            }
            
            .announcement-date {
              color: #8c939d;
              font-size: 13px;
            }
          }
          
          .announcement-content {
            p {
              margin: 5px 0;
              
              &.announcement-desc {
                color: #676a6c;
                font-size: 14px;
              }
            }
          }
        }
      }
    }
    
    .contact-card {
      .contact-info {
        p {
          margin-bottom: 12px;
          display: flex;
          align-items: center;
          color: #676a6c;
          
          i {
            margin-right: 10px;
            color: #4b6df2;
            font-size: 16px;
          }
          
          .el-link {
            color: #4b6df2;
            margin-left: 5px;
          }
        }
        
        .version-stats {
          margin-top: 15px;
          color: #676a6c;
          font-size: 14px;
          
          i {
            margin-right: 5px;
          }
        }
      }
    }
  }
  
  .footer {
    text-align: center;
    padding: 25px 0;
    color: #8c939d;
    font-size: 14px;
    border-top: 1px solid #f0f2f5;
    
    p {
      margin: 5px 0;
      
      a {
        color: #4b6df2;
        text-decoration: none;
        
        &:hover {
          text-decoration: underline;
        }
      }
    }
  }
}

@media (max-width: 768px) {
  .banner .banner-content h1 {
    font-size: 28px;
  }
  
  .banner .banner-content .tagline {
    font-size: 16px;
  }
  
  .section {
    padding: 20px;
    
    .section-title {
      font-size: 20px;
    }
    
    .timeline {
      .timeline-item {
        padding: 20px 0;
        
        .timeline-dot {
          left: -30px;
        }
      }
    }
  }
}
</style>