<template>
  <div id="basicLayout">
<!-- min-height:100vh:  this element is equal to 100% of the viewport height.  -->
    <a-layout style="min-height:100vh">
      <a-layout-header class = 'header'>
        <GlobalHeader/>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view/>
      </a-layout-content>
      <a-layout-footer class="footer">
        <a href="https://stefenz.com" target="_blank">Picture System By Stephen</a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>
<script setup lang="ts">

import GlobalHeader from '@/components/globalHeader.vue'

</script>

<style scoped>
#basicLayout .header {
  padding-inline: 20px;
  margin-bottom: 16px;
  background: white;
  color: unset;
}
.content {
  padding: 20px;
  margin-bottom: 28px;
}

.footer{
  background-color: #efefef;
  padding: 16px;
  position: fixed;
  /**
  //once use the position fixed, the height and with with auto become relative to the content, so we need to adjust the height and weight
  //distance to the buttom is 0, basically mean fixed at the buttom
  //left, right : 0 will achieve 100% width

 */
  bottom: 0;
  left:0;
  right: 0;
  text-align:center;
}
</style>
