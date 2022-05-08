package com.mz.mobaspects.network.message;

/*
public class InputMessage {

    public int key;

    public InputMessage(){}

    public InputMessage(int key){
        this.key = key;
    }

    public static void encode(InputMessage message , PacketBuffer buffer){
        buffer.writeInt(message.key);
    }

    // Remember use same order as encode()
    public static InputMessage decode(PacketBuffer buffer){
        return new InputMessage(buffer.readInt());
    }

    public static void handle(InputMessage message , Supplier<NetworkEvent.Context> contextSupplier){
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork( () -> {
            ServerPlayerEntity player = context.getSender();
            if(player != null) {
                // player.world.getEntityByID()
                player.addExperienceLevel(1);

                    // Vec3 playerLookVector = player.getLookVec();
	            	// EntityMysteriousStranger entityToSpawn = new EntityMysteriousStranger(worldObj);
	                // double spawnX = player.posX+5*playerLookVector.xCoord;
	                // double spawnZ = player.posZ+5*playerLookVector.zCoord;
	                // double spawnY = worldObj.getHeightValue((int)spawnX, (int)spawnZ);

                FireballEntity fireball = new FireballEntity(player.getEntityWorld() , player , 2.0 , 0.0 , 0.0);
                player.getEntityWorld().addEntity(fireball);
            }
            // Already the server world
            // player.getEntityWorld()
        });

        context.setPacketHandled(true);
    }
}
*/