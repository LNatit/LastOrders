package com.lnatit.lastorders.api;

import com.lnatit.lastorders.LastOrders;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

public class EventStack {
    public static final EventStack INSTANCE = new EventStack();
    private final Map<Class<?>, Function<?, Event>> eventPairs = new HashMap<>();
    private final LinkedList<Event> eventStack = new LinkedList<>();

    public EventStack() {
        putEventPair(ClientTickEvent.Pre.class, p -> new ClientTickEvent.Post());
        putEventPair(ClientPauseChangeEvent.Pre.class,
                p -> new ClientPauseChangeEvent.Post(p.isPaused()));

        putEventPair(InputEvent.MouseButton.Pre.class,
                p -> new InputEvent.MouseButton.Post(p.getButton(), p.getAction(), p.getModifiers()));

        putEventPair(ScreenEvent.Init.Pre.class, PostScreenInitWrapper::new);
        putEventPair(ScreenEvent.Render.Pre.class,
                p -> new ScreenEvent.Render.Post(p.getScreen(), p.getGuiGraphics(), p.getMouseX(), p.getMouseY(), p.getPartialTick()));
        putEventPair(ScreenEvent.KeyPressed.Pre.class,
                p -> new ScreenEvent.KeyPressed.Post(p.getScreen(), p.getKeyCode(), p.getScanCode(), p.getModifiers()));
        putEventPair(ScreenEvent.KeyReleased.Pre.class,
                p -> new ScreenEvent.KeyReleased.Post(p.getScreen(), p.getKeyCode(), p.getScanCode(), p.getModifiers()));
        putEventPair(ScreenEvent.MouseDragged.Pre.class,
                p -> new ScreenEvent.MouseDragged.Post(p.getScreen(), p.getMouseX(), p.getMouseY(), p.getMouseButton(), p.getDragX(), p.getDragY()));
        putEventPair(ScreenEvent.MouseScrolled.Pre.class,
                p -> new ScreenEvent.MouseScrolled.Post(p.getScreen(), p.getMouseX(), p.getMouseY(), p.getScrollDeltaX(), p.getScrollDeltaY()));
        putEventPair(ScreenEvent.CharacterTyped.Pre.class,
                p -> new ScreenEvent.CharacterTyped.Post(p.getScreen(), p.getCodePoint(), p.getModifiers()));
        putEventPair(ScreenEvent.MouseButtonPressed.Pre.class,
                p -> new ScreenEvent.MouseButtonPressed.Post(p.getScreen(), p.getMouseX(), p.getMouseY(), p.getButton(), true));
        putEventPair(ScreenEvent.MouseButtonReleased.Pre.class,
                p -> new ScreenEvent.MouseButtonReleased.Post(p.getScreen(), p.getMouseX(), p.getMouseY(), p.getButton(), true));

        putEventPair(RenderGuiEvent.Pre.class,
                p -> new RenderGuiEvent.Post(p.getGuiGraphics(), p.getPartialTick()));
        putEventPair(RenderFrameEvent.Pre.class,
                p -> new RenderFrameEvent.Post(p.getPartialTick()));
        putEventPair(RenderPlayerEvent.Pre.class,
                p -> new RenderPlayerEvent.Post(p.getEntity(), p.getRenderer(), p.getPartialTick(), p.getPoseStack(), p.getMultiBufferSource(), p.getPackedLight()));
        putEventPair(RenderLivingEvent.Pre.class,
                p -> new RenderLivingEvent.Post<>(p.getEntity(), p.getRenderer(), p.getPartialTick(), p.getPoseStack(), p.getMultiBufferSource(), p.getPackedLight()));
        putEventPair(RenderGuiLayerEvent.Pre.class,
                p -> new RenderGuiLayerEvent.Post(p.getGuiGraphics(), p.getPartialTick(), p.getName(), p.getLayer()));

        // TODO need inspection
//        putEventPair(Pre, );
    }

    public void push(Event event) {
        Class<?> preClass = event.getClass();
        if (this.eventPairs.containsKey(preClass)) {
            this.eventStack.push(event);
        }
    }

    public void pop() {
        this.eventStack.pop();
    }

    public void postAllRemaining() {
        try {
            for (int i = 0; i < this.eventStack.size(); i++) {
                Event pre = this.eventStack.getFirst();
                Event post = eventPairs.get(pre.getClass()).apply(pre);
                NeoForge.EVENT_BUS.post(post);
            }
        } catch (Throwable fatal) {
            LastOrders.LOGGER.error("Last Orders got fatal exception during post-crash procedure, quitting game...");
            throw fatal;
        }
    }

    public <P extends Event> void putEventPair(Class<P> preClass, Function<P, Event> transfer) {
        this.eventPairs.put(preClass, transfer);
    }

    private static class PostScreenInitWrapper extends ScreenEvent.Init.Post {
        final ScreenEvent.Init.Pre pre;

        public PostScreenInitWrapper(ScreenEvent.Init.Pre pre) {
            super(pre.getScreen(), pre.getListenersList(), l -> {}, l -> {});
            this.pre = pre;
        }

        @Override
        public void addListener(GuiEventListener listener) {
            pre.addListener(listener);
        }

        @Override
        public void removeListener(GuiEventListener listener) {
            pre.removeListener(listener);
        }
    }
}
