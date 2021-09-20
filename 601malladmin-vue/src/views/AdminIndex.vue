<template>
    <div class="adminIndex-div">
        <div>
            <el-image
                    :src="require('@/assets/admin_index.jpg')"
                    style="width: 540px; height: 480px;"></el-image>
        </div>
    </div>
</template>

<script>
    export default {
        name: "AdminIndex",
    }
</script>

<style scoped>

    .adminIndex-div {
        text-align: center;
        display:flex;
        justify-content:center;
        align-items:center;
    }

</style>